package com.example.gw2api

data class Settings(
    val duration: Any,
    val name: String,
    val scoring: String,
    val tiers: List<TierX>
)