package com.example.tdm

import android.app.Application
import android.text.BoringLayout
import com.example.tdm.DATA.Model.Commande
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.Model.Traitement
import com.example.tdm.DATA.ViewModel.PharmacyViewModel

class MyApplication : Application() {

    companion object {
        lateinit var pharmacies : List<Pharmacy>
        var pharmaciesChanged : Boolean = false

        lateinit var commandes : List<Commande>
        var commandesChanged : Boolean = false

        lateinit var Traitements : List<Traitement>
        var TraitementsChanged : Boolean = false


    }


    fun getPharmacies(): List<Pharmacy> {
        return pharmacies
    }

    fun setPharmacies(pharmas : List<Pharmacy>) {
        pharmacies = pharmacies
    }


}