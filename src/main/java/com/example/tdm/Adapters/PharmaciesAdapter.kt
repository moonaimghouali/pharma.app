package com.example.tdm.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tdm.API.RetrofitService
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.PharmacyActivity
import com.example.tdm.R
import retrofit2.Retrofit

class PharmaciesAdapter(val context: Context,var data1:List<Pharmacy>):RecyclerView.Adapter<PharmaciesAdapter.MyViewHolder>() {

    private var data = emptyList<Pharmacy>()
    class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val pharmacyName = view.findViewById<TextView>(R.id.tv_pharmcieName) as TextView
        val adress = view.findViewById<TextView>(R.id.tv_adress) as TextView
        val phone = view.findViewById<TextView>(R.id.tv_phone) as TextView
        val image = view.findViewById<ImageView>(R.id.pharmacyImage) as ImageView
        val location = view.findViewById<ImageView>(R.id.location) as ImageView
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pharmacie, parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.pharmacyName.text = data[position].nom
        holder.adress.text = data[position].adress
        holder.phone.text = data[position].num_tel

        Glide.with(context).load("${RetrofitService.baseURL}/img${data[position].id_pharmacy}.jpg").into(holder.image)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, PharmacyActivity::class.java)
            intent.putExtra("id", data[position].id_pharmacy)
            context.startActivity(intent)
        }

        holder.location.setOnClickListener {
            val latitude = data[position].latitude
            val longitude = data[position].longtitude
            //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=<lat>,<long>(Label+Name)"))
            val geoLocation = Uri.parse("geo:$latitude,$longitude?z=85")
            val intent = Intent(Intent.ACTION_VIEW,geoLocation)

            if (intent.resolveActivity((context as Activity).packageManager) != null) {
                startActivity(context,intent,null)
            }
        }
    }

    override fun getItemCount()  = data.size

    fun setData(pharmacies : List<Pharmacy>){
        this.data = pharmacies
        notifyDataSetChanged()
    }
}