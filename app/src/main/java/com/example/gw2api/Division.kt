package com.example.gw2api

data class Division(
    val flags: List<String>,
    val large_icon: String,
    val name: String,
    val pip_icon: String,
    val small_icon: String,
    val tiers: List<Tier>
)