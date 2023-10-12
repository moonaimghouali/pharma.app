package com.example.tdm.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.Model.Traitement
import com.example.tdm.R

class TraitementsAdapter(val context: Context, var data1:List<Traitement>): RecyclerView.Adapter<TraitementsAdapter.MyViewHolder>() {

    private var data = emptyList<Traitement>()
    class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val traitementId = view.findViewById<TextView>(R.id.tv_traitementId) as TextView
        val dateDebut = view.findViewById<TextView>(R.id.tv_dateDebut) as TextView
        val duree = view.findViewById<TextView>(R.id.tv_Duree) as TextView
        val medicament = view.findViewById<TextView>(R.id.tv_medicament) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_traitement, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.traitementId.text = "Traitement N"+data[position].id_client.toString()+"_"+data[position].id_traitement.toString()
        holder.dateDebut.text  = data[position].date_debut
        holder.duree.text = data[position].duree.toString()+" Jrs"
        for (med in data[position].medicaments){
            holder.medicament.text = holder.medicament.text.toString() + med.medicament+": "+med.quantite+" Cpt/J" +"\n"
        }
    }

    override fun getItemCount()  = data.size

    fun setData(traitements : List<Traitement>){
        this.data = traitements
        notifyDataSetChanged()
    }
}