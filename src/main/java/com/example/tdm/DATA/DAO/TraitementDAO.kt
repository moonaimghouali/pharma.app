package com.example.tdm.DATA.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.tdm.DATA.Model.Traitement

@Dao
interface TraitementDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraitement(traitement: Traitement)

    @Query("SELECT * FROM Traitement WHERE Traitement.id_client = :id_client")
    fun getTraitements(id_client : Int): LiveData<List<Traitement>>
}