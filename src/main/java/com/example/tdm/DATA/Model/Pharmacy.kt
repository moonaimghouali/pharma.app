package com.example.tdm.DATA.Model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Pharmacy")
data class Pharmacy  (
    @PrimaryKey(autoGenerate = true)
    val id_pharmacy: Int,
    val nom: String,
    val ville: String,
    val adress: String,
    val latitude: Float,
    val longtitude: Float,
    val num_tel: String,
    val url_fb: String

)
