package com.example.tdm.DATA.Repository

import androidx.lifecycle.LiveData
import com.example.tdm.DATA.DAO.CommandeDAO
import com.example.tdm.DATA.Model.Commande


class CommandeRepository(private val CommandeDAO: CommandeDAO, id_client:Int) {
    val readAllCommandeData : LiveData<List<Commande>> = CommandeDAO.getCommandes(id_client)

    suspend fun addCommande(commande: Commande){
        CommandeDAO.insertCommande(commande)
    }
}