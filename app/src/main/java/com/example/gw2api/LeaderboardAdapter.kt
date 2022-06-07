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
            playerStanding.text = names[position].rank.toString()
            playerName.text = names[position].name
            playerRating.text = names[position].scores[0].value.toString()
            playerWins.text = names[position].scores[1].value.toString()
            playerLosses.text = names[position].scores[2].value.toString()
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun setListData(data: Leaderboard) {
        this.names = data
    }
}