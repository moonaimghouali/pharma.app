package com.example.tdm.DATA.Model

import androidx.room.Entity

@Entity(tableName = "Horaire")
data class PharmacyHoraire(
    val jour : Int,
    val heure_ouverture : String,
    val heure_fermeture :String
)
