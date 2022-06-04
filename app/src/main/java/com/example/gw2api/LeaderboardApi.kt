package com.example.gw2api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeaderboardApi {

    @GET("{id}/leaderboards/ladder/eu")
    suspend fun getResponse(
        @Path("id") id: String
    ): Response<Leaderboard>


}