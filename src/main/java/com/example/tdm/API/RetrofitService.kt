package com.example.tdm.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    val baseURL = "https://3a94-41-107-109-134.ngrok.io/"

    val endpoint =
        Retrofit.Builder().baseUrl(baseURL). addConverterFactory(
            GsonConverterFactory.create()). build().create(Endpoint::class.java)
}