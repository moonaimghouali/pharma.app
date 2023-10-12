package com.example.tdm.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Information")
data class PharmacyInformation(
    @PrimaryKey(autoGenerate = true)
    val id_information :Int,
    val info_title : String,
    val info_detail : String
)
