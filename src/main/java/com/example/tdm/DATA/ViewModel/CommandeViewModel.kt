package com.example.tdm.DATA.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tdm.DATA.Model.Commande
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.PharmacyDB
import com.example.tdm.DATA.Repository.CommandeRepository
import com.example.tdm.DATA.Repository.PharmacyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CommandeViewModel(application: Application): AndroidViewModel(application) {

    val readAllData : LiveData<List<Commande>>
    private var repository: CommandeRepository

    init {
        val commandeDao = PharmacyDB.getDatabase(application).commandeDAO()
        repository = CommandeRepository(commandeDao,1)
        readAllData = repository.readAllCommandeData
    }

    fun addCommande(commande: Commande){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCommande(commande)
        }
    }
}