package com.example.gw2api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LeaderboardApi {

    // Display the leaderboard for EU
    @GET("seasons/{id}/leaderboards/ladder/{region}")
    suspend fun getLeaderboard(
        @Path("id") id: String,
        @Path("region") region: String
    ): Response<Leaderboard>

    // Get the list od IDs
    @GET("seasons")
    suspend fun getLeaderboardList(): Response<SeasonList>

    // Get the specific season's name
    @GET("seasons/{id}")
    suspend fun getLeaderboardName(
        @Path("id") id: String
    ): Response<SeasonDetails>
}