package com.example.tdm.DATA.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tdm.DATA.Model.Commande


@Dao
interface CommandeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommande(commande: Commande)

    @Query("SELECT * FROM Commande WHERE Commande.id_client = :id_client")
    fun getCommandes(id_client:Int): LiveData<List<Commande>>
}