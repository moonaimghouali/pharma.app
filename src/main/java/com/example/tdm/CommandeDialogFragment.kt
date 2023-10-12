package com.example.tdm

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_commande_dialog.*

class CommandeDialogFragment(id_commande : Int) : DialogFragment() {
    val idc = id_commande
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder= AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.fragment_commande_dialog, null))
                .setPositiveButton("ok",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onStart() {
        super.onStart()
        //Glide.with(this.requireActivity()).load("http://goo.gl/gEgYUd").into(commandeImage2)
    }

}