package com.example.gw2api

data class LeaderboardItem(
    val date: String,
    val name: String,
    val rank: Int,
    val scores: List<Score>
)