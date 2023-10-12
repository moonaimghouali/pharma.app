package com.example.tdm.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm.CommandeDialogFragment
import com.example.tdm.DATA.Model.Commande
import com.example.tdm.HorraireDialog
import com.example.tdm.R
import android.app.Activity
import com.example.tdm.DATA.Model.Pharmacy
import androidx.fragment.app.FragmentActivity





class CommandesAdapter(val context: Context, var data1:List<Commande>): RecyclerView.Adapter<CommandesAdapter.MyViewHolder>() {

    private var data :List<Commande> = emptyList()
    class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val commandeName = view.findViewById<TextView>(R.id.tv_traitementId) as TextView
        val pharmacieName = view.findViewById<TextView>(R.id.tv_pharmcieName) as TextView
        val commandeDate = view.findViewById<TextView>(R.id.tv_commandeDate) as TextView
        val commandeEtat = view.findViewById<TextView>(R.id.tv_commandeEtat) as TextView
        val commandeImage = view.findViewById<ImageView>(R.id.cmdImage) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_commande, parent, false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.commandeName.text = "Commande N " + (data[position].id_commande).toString() +"_"+(data[position].id_pharmacy).toString()
        holder.pharmacieName.text = data[position].nom
        holder.commandeDate.text = data[position].date_commande
        holder.commandeEtat.text = data[position].etat
        holder.commandeImage.setOnClickListener {

            val  commandeDialogue =  CommandeDialogFragment(data[position].id_commande)
            commandeDialogue.show((context as FragmentActivity).supportFragmentManager,"commande")
        }


        if (data[position].etat =="Traitee"){
            holder.commandeEtat.setTextColor(Color.parseColor("#2BC622"))
        }else{
            if (data[position].etat =="lancée"){
                holder.commandeEtat.setTextColor(Color.parseColor("#424242"))
            }else{
                if (data[position].etat =="en cours de traitement"){
                    holder.commandeEtat.setTextColor(Color.parseColor("#F48806"))
                }else{
                    if (data[position].etat =="rejetée"){
                        holder.commandeEtat.setTextColor(Color.parseColor("#DB1919"))
                    }
                }
            }
        }

    }

    override fun getItemCount()  = data.size

    fun setData(commandes : List<Commande>){
        this.data = commandes
        notifyDataSetChanged()
    }

}