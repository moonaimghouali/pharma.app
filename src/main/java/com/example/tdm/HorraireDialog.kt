package com.example.tdm

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.tdm.DATA.Model.PharmacyHoraire
import kotlinx.android.synthetic.main.dialogue_horraires.*
import java.util.*

class HorraireDialog(private val data : List<PharmacyHoraire>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder= AlertDialog.Builder(it)

        val inflater = requireActivity().layoutInflater;

        builder.setView(inflater.inflate(R.layout.dialogue_horraires, null))
            .setPositiveButton("ok",
                DialogInterface.OnClickListener { dialog, id ->
                })

        builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (item in data){
            Log.d("horaire",item.jour.toString())
            //if (item.jour == 1) tv_horraireDimanche.text = (""+item.heure_ouverture+" - "+item.heure_fermeture).toString()
            //tv_horraireDimanche.text = "hhsaas"
//            when (item.jour) {
//                1 -> { tv_horraireDimanche.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                2 -> { tv_horraireLundi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                3 -> { tv_horraireMardi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                4 -> { tv_horraireMercredi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                5 -> { tv_horraireJeudi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                6 -> { tv_horraireVendredi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//                7 -> { tv_horraireSamedi.text = ""+item.heure_ouverture+" - "+item.heure_fermeture}
//            }
        }
    }
}