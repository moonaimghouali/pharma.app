package com.example.tdm

import android.content.Context
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
import com.example.tdm.Adapters.PharmaciesAdapter

import com.example.tdm.DATA.Model.Commande
import com.example.tdm.DATA.ViewModel.CommandeViewModel
import com.example.tdm.DATA.ViewModel.PharmacyViewModel
import com.example.tdm.R
import com.example.tdm.Util.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_commandes.*
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommandesFragment : Fragment(R.layout.fragment_commandes) {

    private lateinit var mCommandeViewModel : CommandeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun requestData() {
        val pref = activity?.getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return

        val connected = pref?.getString("status","not connected")
        if (connected == "connected"){
            val id_client = pref!!.getInt("id_client",-1)
            if (isNetworkAvailable(context)){
                getData(id_client)
                Toast.makeText(context, "id client = ${id_client} , status = ${connected}", Toast.LENGTH_SHORT).show()

            }else{
                getDataLocal(id_client)
            }

        }else{
            Toast.makeText(activity, "you are not logged in return to login page", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getData(id_client: Int) {
        val call = RetrofitService.endpoint.getCommandes(id_client)
        call.enqueue(object: Callback<List<Commande>> {
            override fun onResponse(call: Call<List<Commande>>, response: Response<List<Commande>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null){

                        val adapater = CommandesAdapter(requireContext(),data)
                        adapater.setData(data)
                        rv_commandes.layoutManager = LinearLayoutManager(activity)
                        rv_commandes.adapter = adapater

                        MyApplication.commandes = data
                        MyApplication.commandesChanged = true
                    }
                }
            }
            override fun onFailure(call: Call<List<Commande>>, t: Throwable) {
                Toast.makeText(activity, "un erreur est produite ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataLocal(id_client: Int) {
        val adapater = CommandesAdapter(requireContext(), emptyList())

        rv_commandes.layoutManager = LinearLayoutManager(activity)
        rv_commandes.adapter = adapater
//        mCommandeViewModel = ViewModelProvider(this,)[CommandeViewModel::class.java]
//        mCommandeViewModel.readAllData.observe(this, Observer{commandes ->
//            adapater.setData(commandes)
//        })
    }
}