package com.example.gw2api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gw2api.databinding.LeaderboardItemBinding

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    var names = Leaderboard()

//    inner class StandingsViewHolder(val binding: StandingsItemBinding) : RecyclerView.ViewHolder(binding.root)
    inner class LeaderboardViewHolder(val binding: LeaderboardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder(
            LeaderboardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.binding.apply {
            playerName.text = names[position].name
            playerStanding.text = names[position].rank.toString()
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun setListData(data: Leaderboard) {
        this.names = data
    }
}