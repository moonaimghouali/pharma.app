package com.example.tdm.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.tdm.DATA.Converters.MedicamentConverter

@Entity(tableName = "Traitement")
data class Traitement(
    @PrimaryKey(autoGenerate = true)
    val id_traitement : Int,
    val id_client : Int,
    val date_debut : String,
    val duree : Int,
    val medicaments : List<Medicament>

)
