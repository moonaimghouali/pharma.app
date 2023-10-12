package com.example.tdm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pharmacy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import com.bumptech.glide.Glide
import com.example.tdm.API.RetrofitService
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.Model.PharmacyHoraire
import com.example.tdm.DATA.Model.PharmacyInformation
import com.example.tdm.Util.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialogue_cmd_choix.*
import kotlinx.android.synthetic.main.dialogue_horraires.*
import okhttp3.MediaType

import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File


class PharmacyActivity : AppCompatActivity() {


    var id_client =0
    lateinit var pharmacie :Pharmacy
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return
        val status = pref.getString("status","not connected")
        id_client = pref.getInt("id_client",0)

        var id_pharmacy= intent.getIntExtra("id",0)
        getData(id_pharmacy)
        getInformation(id_pharmacy)
        Toast.makeText(this, getDate(), Toast.LENGTH_LONG).show()

        btn_commander.setOnClickListener {
            if (status =="connected"){
                showCommandeDialogue()
            }else{
                Toast.makeText(this, "you are not logged in", Toast.LENGTH_SHORT).show()
            }
        }

        btn_horraires.setOnClickListener {
            getHoraire(id_pharmacy)
        }

        btn_facebook.setOnClickListener{
            openFacebookURL(pharmacie.url_fb)
        }

        btn_phone.setOnClickListener {
            val uri = Uri.parse("tel:${pharmacie.num_tel}")
            val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
        }

        btn_location.setOnClickListener {
            val latitude = pharmacie.latitude
            val longitude = pharmacie.longtitude
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=<lat>,<long>(Label+Name)"))
            val geoLocation = Uri.parse("geo:$latitude,$longitude?z=90")
            val intent = Intent(Intent.ACTION_VIEW,geoLocation)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }

    private fun showHorrairesDialogue(data : List<PharmacyHoraire>) {
        val  HoraireDialogue =  HorraireDialog(data)
        HoraireDialogue.show(supportFragmentManager,"Horraires")

    }

    private fun showCommandeDialogue() {
        ImagePicker.with(this)
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .crop(12f,16f)
            .start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!

            uploadFile(uri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadFile(uri : Uri) {

        //image part
        val image = File(uri.path!!)
        val type= image.extension
        val requestFile: RequestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
        val commandeImage = MultipartBody.Part.createFormData("image", image.name, requestFile)

        //body
        val call = RetrofitService.endpoint.addCommande(image = commandeImage)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.v("Upload", "success")
                Toast.makeText(this@PharmacyActivity, "successful upload", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("Upload error:", t.message!!)
                Toast.makeText(this@PharmacyActivity, "failed upload", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openFacebookURL(url_fb : String) {
        val facebookExist :Boolean = isPackageInstalled(this , "com.facebook.katana")

        val url =url_fb
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

//        if (facebookExist == true){
//            newFacebookIntent(packageManager,url_fb)
//        }else{
//            val url =url_fb
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            startActivity(intent)
//        }
    }


    private fun getData(id_pharmacy: Int) {
        val call = RetrofitService.endpoint.getPharmacie(id_pharmacy)
        call.enqueue(object: Callback<Pharmacy> {
            override fun onResponse(call: Call<Pharmacy>, response: Response<Pharmacy>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        tv_pharmacieName2.text = data.nom
                        tv_adress2.text = data.adress
                        tv_phone2.text = data.num_tel
                        pharmacie  = Pharmacy(data.id_pharmacy,data.nom,data.ville,data.adress,data.latitude,data.longtitude,data.num_tel,data.url_fb)
                        Glide.with(this@PharmacyActivity).load("${RetrofitService.baseURL}/img${data.id_pharmacy}.jpg").into(pharmacy_image)
                    }
                }
            }

            override fun onFailure(call: Call<Pharmacy>, t: Throwable) {
                //t.printStackTrace()
                Toast.makeText(this@PharmacyActivity, "MNMun erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getHoraire(id_pharmacy: Int) {
        val call = RetrofitService.endpoint.getPharmacieHoraires(id_pharmacy)
        call.enqueue(object: Callback<List<PharmacyHoraire>> {
            override fun onResponse(call: Call<List<PharmacyHoraire>>, response: Response<List<PharmacyHoraire>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        showHorrairesDialogue(data)
                    }
                }
            }
            override fun onFailure(call: Call<List<PharmacyHoraire>>, t: Throwable) {
                Toast.makeText(this@PharmacyActivity, "un erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getInformation(id_pharmacy: Int) {
        val call = RetrofitService.endpoint.getPharmacieInformations(id_pharmacy)
        call.enqueue(object: Callback<List<PharmacyInformation>> {
            override fun onResponse(call: Call<List<PharmacyInformation>>, response: Response<List<PharmacyInformation>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        for (item in data){
                              tv_informations.text = tv_informations.text.toString()+ item.info_title +" : "+item.info_detail +"\n\n"
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<PharmacyInformation>>, t: Throwable) {
                //t.printStackTrace()
                Toast.makeText(this@PharmacyActivity, "un erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

}