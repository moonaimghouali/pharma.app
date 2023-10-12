package com.example.tdm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tdm.API.RetrofitService
import com.example.tdm.DATA.Model.User
import com.example.tdm.Util.md5
import com.example.tdm.Util.verifyLoginInput
import kotlinx.android.synthetic.main.fragment_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences




class SignUpFragment : Fragment(R.layout.fragment_signup) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_signUP.setOnClickListener {
            Inscription()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        errors2.text =""
//    }

    private fun Inscription() {

        if (!verifyLoginInput(et_signEmail.text.toString(),et_signPassword.text.toString())){
            errors2.text = "you must fill all the fields"
            return
        }

        val fullname = tv_signFullName.text
        val emailHash = (et_signEmail.text).toString().md5()
        val passwordHash = (et_signPassword.text).toString().md5()

        val user = User(emailHash,passwordHash)

        val call = RetrofitService.endpoint.Inscription(user)
        call.enqueue(object: Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if(response.isSuccessful){
                    val res = response.body()
                    val id_client = res?.get(0)
                    if(id_client == -1){
                        Toast.makeText(context, "vous etes deja inscrit", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "your id is =${id_client}", Toast.LENGTH_LONG).show()
                        inscrireUser(id_client)
                    }
                }
            }
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(context, "sign up has failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun inscrireUser(idClient: Int?) {

        val pref  = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return

        with(pref.edit()) {
            putString("status","connected")
            putInt("id_client", idClient!!)
            apply()
        }

        val intent = Intent(activity,HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }




}