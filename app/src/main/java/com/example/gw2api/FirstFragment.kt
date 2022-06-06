package com.example.gw2api

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.gw2api.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment(R.layout.fragment_first), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentFirstBinding
    private val leaderboardsViewModel: LeaderboardsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        val leaderboardObserver = Observer<Leaderboard> {
        }
        val leaderboardDetailsObserver = Observer<List<String>> {
        }
        val leaderboardListObserver = Observer<SeasonList> {
        }
        leaderboardsViewModel.leaderboardLd.observe(viewLifecycleOwner, leaderboardObserver)
        leaderboardsViewModel.leaderboardDetails.observe(viewLifecycleOwner, leaderboardDetailsObserver)
        leaderboardsViewModel.leaderboardListLd.observe(viewLifecycleOwner, leaderboardListObserver)

                lifecycleScope.launch(Dispatchers.IO) {
                    leaderboardsViewModel.getLeaderboard()
                    leaderboardsViewModel.getLeaderboardList()
                    withContext(Main) {
                        leaderboardsViewModel.getLeaderboardDetails()
                        setupSpinner()
                    }
        }
    }

    private fun setupSpinner() {
        val spinner = binding.seasonSpinner
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            leaderboardsViewModel.leaderboardDetailsList
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}