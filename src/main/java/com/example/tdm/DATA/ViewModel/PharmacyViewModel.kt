package com.example.tdm.DATA.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.PharmacyDB
import com.example.tdm.DATA.Repository.PharmacyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PharmacyViewModel(application: Application): AndroidViewModel(application) {

    val readAllData : LiveData<List<Pharmacy>>
    val readVilleData : LiveData<List<Pharmacy>>
    private var repository: PharmacyRepository

    init {
        val pharmacyDao = PharmacyDB.getDatabase(application).pharmacyDAO()
        repository = PharmacyRepository(pharmacyDao,"All")
        readAllData = repository.readAllPharmacyData
        readVilleData = repository.readVillePharmacyData
    }

    fun addPharmacy(pharmacy: Pharmacy){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPharmacy(pharmacy)
        }
    }
}