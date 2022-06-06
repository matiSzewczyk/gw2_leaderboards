package com.example.gw2api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaderboardsViewModel : ViewModel() {

    var leaderboardLd = MutableLiveData<Leaderboard>()
    var leaderboardListLd = MutableLiveData<SeasonList>()
    var leaderboardDetails = MutableLiveData<MutableList<String>>()
    var leaderboardDetailsList = mutableListOf<String>()

    private val seasonId = "E9191774-2EB8-4C74-BF57-7236DF40A16F"

    suspend fun getLeaderboard() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboard(seasonId)
        leaderboardLd.postValue(response.body()!!)
    }

    suspend fun getLeaderboardList() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardList()
        leaderboardListLd.postValue(response.body()!!)
    }

    suspend fun getLeaderboardDetails() {
        if (leaderboardDetailsList.isEmpty()) {
            leaderboardListLd.value!!.forEach {
                val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardName(it)
                leaderboardDetailsList.add(response.body()!!.name)
                leaderboardDetails.value = leaderboardDetailsList
            }
        }
    }
}