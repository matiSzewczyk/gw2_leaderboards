package com.example.gw2api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val api: LeaderboardApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.guildwars2.com/v2/pvp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}