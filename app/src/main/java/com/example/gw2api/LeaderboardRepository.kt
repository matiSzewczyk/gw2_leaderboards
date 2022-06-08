package com.example.gw2api

class LeaderboardRepository(
    private val leaderboardApi: LeaderboardApi
) {
    suspend fun getLeaderboard(seasonId: String, region: String) = leaderboardApi.getLeaderboard(seasonId, region)
    suspend fun getLeaderboardList() = leaderboardApi.getLeaderboardList()
    suspend fun getLeaderboardName(seasonId: String) = leaderboardApi.getLeaderboardName(seasonId)
}