package com.example.gw2api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaderboardsViewModel : ViewModel() {

    var leaderboardList = MutableLiveData<List<Leaderboard>>()
    private val seasonId = "E9191774-2EB8-4C74-BF57-7236DF40A16F"

    suspend fun getLeaderboard() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboard(seasonId)
        leaderboardList.postValue(mutableListOf(response.body()!!))
    }
}