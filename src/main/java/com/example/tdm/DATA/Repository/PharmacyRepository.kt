package com.example.tdm.DATA.Repository

import androidx.lifecycle.LiveData
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.DAO.PharmacyDAO

class PharmacyRepository(private val PharmacyDAO:PharmacyDAO, choix:String) {
    val readAllPharmacyData : LiveData<List<Pharmacy>> = PharmacyDAO.getPharmacies()
    val readVillePharmacyData : LiveData<List<Pharmacy>> = PharmacyDAO.getVillePharmacies(choix)

    suspend fun addPharmacy(pharmacy: Pharmacy){
        PharmacyDAO.insertPharmacy(pharmacy)
    }
}