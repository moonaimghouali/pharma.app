package com.example.tdm

import android.app.Dialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialogue_cmd_choix.*

class CmdChoixDialogFragment(uri :Uri) : DialogFragment() {
    val uri = uri
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder= AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.dialogue_cmd_choix, null))

                .setPositiveButton("Envoyer",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                .setNegativeButton("annuler",
                    DialogInterface.OnClickListener { dialog, id ->
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    override fun onStart() {
        super.onStart()
        //val img = view?.findViewById<ImageView>(R.id.imageView2) as ImageView
    }



}