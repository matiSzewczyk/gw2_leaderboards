package com.example.gw2api

data class SeasonDetails(
    val active: Boolean,
    val divisions: List<Division>,
    val end: String,
    val id: String,
    val leaderboards: Leaderboards,
    val name: String,
    val ranks: List<Rank>,
    val start: String
)