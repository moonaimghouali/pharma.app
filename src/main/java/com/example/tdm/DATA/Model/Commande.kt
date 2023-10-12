package com.example.tdm.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Commande")
data class Commande (
        @PrimaryKey(autoGenerate = true)
        val id_commande :Int,
        val id_client : Int,
        val id_pharmacy : Int,
        val date_commande : String,
        val etat : String ,
        val nom : String
        )