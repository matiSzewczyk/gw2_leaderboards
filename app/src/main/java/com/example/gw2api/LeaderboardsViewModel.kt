package com.example.gw2api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaderboardsViewModel : ViewModel() {

    var lastSelectedSpinnerPosition = 43

    var regionList = listOf(
        "eu",
        "na"
    )
    private var currentRegion = regionList[0]

    var leaderboardLiveData = MutableLiveData<Leaderboard>()
    var seasonNameList = mutableListOf<String>()
    var seasonIdList = SeasonList()

    private var seasonId = "E9191774-2EB8-4C74-BF57-7236DF40A16F"

    suspend fun getLeaderboard() {
        val response = LeaderboardRepository(
            RetrofitInstance.api).getLeaderboard(seasonId, currentRegion
        )
        if (response.isSuccessful) {
            leaderboardLiveData.postValue(response.body()!!)
        } else {
            Log.d("error:", "Failed to load the leaderboard.")
        }
    }

    suspend fun getSeasonList() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardList()
        seasonIdList = response.body()!!
        println("seasonIdList: $seasonIdList")
    }

    suspend fun getSeasonName() {
        if (seasonIdList.isNotEmpty()) {
            seasonIdList.forEach {
                println("\nit: $it")
                val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardName(it.toString())
                seasonNameList.add(response.body()!!.name)
            }
        }
    }

    fun setSeasonId(selectedItemPosition: Int) {
        seasonId = seasonIdList[selectedItemPosition]
    }

    fun setRegion(position: Int) {
        currentRegion = regionList[position]
    }
}