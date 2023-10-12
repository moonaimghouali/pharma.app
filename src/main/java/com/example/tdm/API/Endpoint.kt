package com.example.tdm.API

import com.example.tdm.DATA.*
import retrofit2.Call
import com.example.tdm.DATA.Model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface Endpoint {

    @GET("pharmacies")
    fun getPharmacies() : Call<List<Pharmacy>>

    //Pharmacie detail
    @GET("/pharmacies/{id_pharmacy}")
    fun getPharmacie(@Path("id_pharmacy") id_pharmacy: Int) : Call<Pharmacy>

    @GET("/pharmacies/{id_pharmacy}/horaires")
    fun getPharmacieHoraires(@Path("id_pharmacy") id_pharmacy: Int) : Call<List<PharmacyHoraire>>

    @GET("/pharmacies/{id_pharmacy}/Informations")
    fun getPharmacieInformations(@Path("id_pharmacy") id_pharmacy: Int) : Call<List<PharmacyInformation>>

    // Pharmacies d'une ville
    @GET("/{ville}")
    fun getVillePharmacies(@Path("ville") ville: String) : Call<List<Pharmacy>>

    //Commandes
    @GET("/commandes/{id_client}")
    fun getCommandes(@Path("id_client" ) id_client:Int) : Call<List<Commande>>

    @Multipart
    @POST("/commandes/new")
    fun addCommande(
        @Part("image") image : MultipartBody.Part
//        @Part("id_client") id_client: String,
//        @Part("id_pharmacy") id_pharmacy: String,
//        @Part("nom_pharmacy") nom_pharmacy: String
        ) : Call<Int>


    //Traitements
    @GET("/traitements/{id_client}")
    fun getTraitements(@Path("id_client" ) id_client:Int) : Call<List<Traitement>>

    @POST("/Users/connexion")
    fun authentication(@Body user : User) : Call<List<Int>>

    @POST("/users/new")
    fun Inscription(@Body user : User) : Call<List<Int>>
}