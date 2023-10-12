package com.example.tdm

import com.example.tdm.MyApplication.Companion.pharmacies
import com.example.tdm.MyApplication.Companion.pharmaciesChanged
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tdm.DATA.ViewModel.PharmacyViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdm.API.RetrofitService
import com.example.tdm.Adapters.PharmaciesAdapter
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.Util.isNetworkAvailable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PharmaciesFragment : Fragment(R.layout.fragment_pharmacies) {

    lateinit var pref  : SharedPreferences

    private lateinit var mPharmacyViewModel  :PharmacyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        spinnerVilles.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
             requestData(parent?.getItemAtPosition(pos).toString())
             }
            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        })

        //maps
        btn_closePharmacies.setOnClickListener {

            val latitude = 36.50537012845393
            val longitude = 2.865131

            val geoLocation = Uri.parse("geo:$latitude,$longitude?q=pharmacy")
            val intent = Intent(Intent.ACTION_VIEW,geoLocation)
            // if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            //}
        }


    }




    fun requestData(choix :String) {

        if(isNetworkAvailable(context)){
            getData(choix)
        }else{
//            getDataLocalBDD(choix)
        }
    }



    private fun getData(Choix : String){
        lateinit var call : Call<List<Pharmacy>>
        if (Choix == "All"){
            call = RetrofitService.endpoint.getPharmacies()
        }
        else{
            call = RetrofitService.endpoint.getVillePharmacies(Choix)
        }

        call.enqueue(object: Callback<List<Pharmacy>> {
            override fun onResponse(call: Call<List<Pharmacy>>, response: Response<List<Pharmacy>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        val adapater = PharmaciesAdapter(requireContext(),data)
                        adapater.setData(data)
                        rv_pharmacies.layoutManager = LinearLayoutManager(activity)
                        rv_pharmacies.adapter = adapater

                        pharmacies = data
                        pharmaciesChanged = true
//
//                        (activity?.getApplication() as MyApplication).setPharmacies(data)
//                        (activity?.getApplication() as MyApplication).pharmaciesChanged = true

                    }
                }
            }
            override fun onFailure(call: Call<List<Pharmacy>>, t: Throwable) {
                Toast.makeText(activity, "un erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataLocalBDD(choix: String) {
//        val adapater = PharmaciesAdapter(requireContext(), emptyList())
//
//        rv_pharmacies.layoutManager = LinearLayoutManager(activity)
//        rv_pharmacies.adapter = adapater
//
//        mPharmacyViewModel = ViewModelProvider(this)[PharmacyViewModel::class.java]
//        mPharmacyViewModel.readAllData.observe(this, Observer{pharmacies ->
//            adapater.setData(pharmacies)
//        })
    }
}