package com.example.tdm.DATA.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tdm.DATA.Model.Pharmacy

@Dao
interface PharmacyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPharmacy(pharmacy: Pharmacy)

    @Query("SELECT * FROM Pharmacy")
    fun getPharmacies():LiveData<List<Pharmacy>>

    @Query("SELECT * FROM Pharmacy WHERE Pharmacy.ville = :choix")
    fun getVillePharmacies(choix : String):LiveData<List<Pharmacy>>
}