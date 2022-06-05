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

        val leaderboardObserver = Observer<Leaderboard> {
            leaderboardsViewModel.leaderboardLd.value!!.forEach { println("Player names: ${it.name}") }
//            println("\nsecond: ${leaderboardsViewModel.leaderboardLd.value!![2].name}")
        }
        val leaderboardDetailsObserver = Observer<SeasonDetails> {
            println("Season name: ${leaderboardsViewModel.leaderboardDetails.value!!.name}")
        }

        val leaderboardListObserver = Observer<SeasonList> {
            leaderboardsViewModel.leaderboardListLd.value!!.forEach { println("List of season ids: $it") }
        }
                leaderboardsViewModel.leaderboardLd.observe(viewLifecycleOwner, leaderboardObserver)
                leaderboardsViewModel.leaderboardDetails.observe(viewLifecycleOwner, leaderboardDetailsObserver)
                leaderboardsViewModel.leaderboardListLd.observe(viewLifecycleOwner, leaderboardListObserver)

                binding.apply {
                    button.setOnClickListener {
                        lifecycleScope.launch(Dispatchers.IO) {
                            leaderboardsViewModel.getLeaderboard()
                            leaderboardsViewModel.getLeaderboardDetails()
                            leaderboardsViewModel.getLeaderboardList()
                        }
                    }
                }

    }
}