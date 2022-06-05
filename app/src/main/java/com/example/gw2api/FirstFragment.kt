package com.example.gw2api

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.gw2api.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstFragment : Fragment(R.layout.fragment_first){

    private lateinit var binding: FragmentFirstBinding
    private val leaderboardsViewModel: LeaderboardsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        val leaderboardObserver = Observer<List<Leaderboard>> {
            println("\nLeaderboard: ${leaderboardsViewModel.leaderboardList.value}")
        }

        leaderboardsViewModel.leaderboardList.observe(viewLifecycleOwner, leaderboardObserver)

        binding.apply {
            button.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    leaderboardsViewModel.getLeaderboard()
                }
            }
        }

    }
}