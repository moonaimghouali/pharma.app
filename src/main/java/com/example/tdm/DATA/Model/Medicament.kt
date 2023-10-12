package com.example.tdm.DATA.Model

import androidx.room.Entity

@Entity(tableName = "Medicament")
data class Medicament(
    val medicament: String,
    val quantite  : String
)
