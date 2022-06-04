package com.example.gw2api

class LeaderboardRepository(
    private val leaderboardApi: LeaderboardApi
) {
    suspend fun getLeaderboard(seasonId: String) = leaderboardApi.getResponse(seasonId)
}