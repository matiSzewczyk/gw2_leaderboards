package com.example.gw2api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaderboardsViewModel : ViewModel() {

    // I use this so that upon screen rotation, the correct spinner position is selected
    var lastSelectedSpinnerPosition = MutableLiveData(0)

    var leaderboardLiveData = MutableLiveData<Leaderboard>()
    var seasonIdLiveData = MutableLiveData<SeasonList>()
    var seasonNameList = MutableLiveData<MutableList<String>>()

    var seasonIdList = mutableListOf<String>()

    private var seasonId = MutableLiveData("E9191774-2EB8-4C74-BF57-7236DF40A16F")

    suspend fun getLeaderboard() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboard(seasonId.value!!)
        if (response.isSuccessful) {
            leaderboardLiveData.postValue(response.body()!!)
        } else {
            println("\nCall failed :c")
        }
    }

    suspend fun getLeaderboardList() {
        val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardList()
        seasonIdLiveData.postValue(response.body()!!)
    }

    suspend fun getLeaderboardDetails() {
        if (seasonIdList.isEmpty()) {
            seasonIdLiveData.value!!.forEach {
                val response = LeaderboardRepository(RetrofitInstance.api).getLeaderboardName(it)
                seasonIdList.add(response.body()!!.name)
                seasonNameList.value = seasonIdList
            }
        }
    }

    fun setSeason(selectedItemPosition: Int) {
        seasonId.value = seasonIdLiveData.value!![selectedItemPosition]
    }
}