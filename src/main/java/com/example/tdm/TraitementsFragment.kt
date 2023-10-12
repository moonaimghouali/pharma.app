package com.example.tdm

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tdm.API.RetrofitService
import com.example.tdm.Adapters.CommandesAdapter
import com.example.tdm.Adapters.TraitementsAdapter
import com.example.tdm.DATA.Model.Traitement
import com.example.tdm.DATA.ViewModel.CommandeViewModel
import com.example.tdm.DATA.ViewModel.TraitementViewModel
import com.example.tdm.R
import com.example.tdm.Util.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_commandes.*
import kotlinx.android.synthetic.main.fragment_traitements.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TraitementsFragment : Fragment(R.layout.fragment_traitements) {
    lateinit var pref  : SharedPreferences
    private lateinit var mTraitementViewModel: TraitementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return
        requestData()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_traitements, container, false)
    }

    private fun requestData() {

        val connected = pref.getString("status","not connected")
        if (connected ==  "connected"){
            val id_client = pref!!.getInt("id_client",-1)
            Toast.makeText(context, "id client = ${id_client} , status = ${connected}", Toast.LENGTH_SHORT).show()
            if (isNetworkAvailable(context)){
                getData(id_client)
            }else{
                getDataLocal(id_client)
            }

        }else{
            Toast.makeText(activity, "you are not logged in return to login page", Toast.LENGTH_SHORT).show()
        }

    }


    private fun getData(id_client : Int) {
        val call = RetrofitService.endpoint.getTraitements(id_client)
        call.enqueue(object: Callback<List<Traitement>> {
            override fun onResponse(call: Call<List<Traitement>>, response: Response<List<Traitement>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){

                        val adapater = TraitementsAdapter(requireContext(),data)
                        adapater.setData(data)
                        rv_traitements.layoutManager = LinearLayoutManager(activity)
                        rv_traitements.adapter = adapater

                        MyApplication.Traitements = data
                        MyApplication.TraitementsChanged = true

                    }
                }
            }
            override fun onFailure(call: Call<List<Traitement>>, t: Throwable) {
                Toast.makeText(activity, "un erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataLocal(id_client: Int) {
        val adapater = TraitementsAdapter(requireContext(), emptyList())

        rv_traitements.layoutManager = LinearLayoutManager(activity)
        rv_traitements.adapter = adapater

        mTraitementViewModel = ViewModelProvider(this)[TraitementViewModel::class.java]
        mTraitementViewModel.readAllData.observe(this, Observer{traitements ->
            adapater.setData(traitements)
        })
    }
}