package com.example.tdm.DATA.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.Model.Traitement
import com.example.tdm.DATA.PharmacyDB
import com.example.tdm.DATA.Repository.PharmacyRepository
import com.example.tdm.DATA.Repository.TraitementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TraitementViewModel(application: Application): AndroidViewModel(application) {

    val readAllData : LiveData<List<Traitement>>
    private var repository: TraitementRepository

    init {
        val traitemntDao = PharmacyDB.getDatabase(application).traitementDAO()
        repository = TraitementRepository(traitemntDao)
        readAllData = repository.readAllTraitementData
    }

    fun addTraitement(traitement: Traitement){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTraitement(traitement)
        }
    }
}


