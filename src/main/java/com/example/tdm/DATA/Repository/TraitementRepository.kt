package com.example.tdm.DATA.Repository

import androidx.lifecycle.LiveData
import com.example.tdm.DATA.DAO.TraitementDAO
import com.example.tdm.DATA.Model.Traitement

class TraitementRepository(private val TraitementDAO: TraitementDAO) {
    val readAllTraitementData : LiveData<List<Traitement>> = TraitementDAO.getTraitements(1)


    suspend fun addTraitement(traitement: Traitement){
        TraitementDAO.insertTraitement(traitement)
    }
}