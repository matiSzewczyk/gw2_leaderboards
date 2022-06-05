package com.example.gw2api

data class Rank(
    val description: String,
    val icon: String,
    val name: String,
    val overlay: String,
    val overlay_small: String,
    val tiers: List<TierXX>
)