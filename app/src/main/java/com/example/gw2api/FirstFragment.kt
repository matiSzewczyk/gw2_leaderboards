package com.example.gw2api

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gw2api.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment(R.layout.fragment_first), AdapterView.OnItemSelectedListener {

    private lateinit var spinner: Spinner
    private lateinit var binding: FragmentFirstBinding
    private val leaderboardsViewModel: LeaderboardsViewModel by viewModels()
    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        setupRecyclerView()

        val leaderboardObserver = Observer<Leaderboard> {
            leaderboardAdapter.setListData(leaderboardsViewModel.leaderboardLd.value!!)
            leaderboardAdapter.notifyDataSetChanged()
        }
        val leaderboardDetailsObserver = Observer<List<String>> {
        }
        val leaderboardListObserver = Observer<SeasonList> {
        }
        leaderboardsViewModel.leaderboardLd.observe(viewLifecycleOwner, leaderboardObserver)
        leaderboardsViewModel.leaderboardDetails.observe(viewLifecycleOwner, leaderboardDetailsObserver)
        leaderboardsViewModel.leaderboardListLd.observe(viewLifecycleOwner, leaderboardListObserver)

                lifecycleScope.launch(Dispatchers.IO) {
                    // TODO: I need to remove this later
                    leaderboardsViewModel.getLeaderboard()
                    // this ^
                    leaderboardsViewModel.getLeaderboardList()
                    withContext(Main) {
                        leaderboardsViewModel.getLeaderboardDetails()
                        setupSpinner()
                    }
        }
    }

    private fun setupSpinner() {
        spinner = binding.seasonSpinner
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            leaderboardsViewModel.leaderboardDetailsList
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        spinner.setSelection(leaderboardsViewModel.leaderboardDetailsList.size - 1)
    }

    private fun setupRecyclerView() = binding.myRecyclerView.apply {
        leaderboardAdapter = LeaderboardAdapter()
        adapter = leaderboardAdapter
        layoutManager = LinearLayoutManager(context)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (spinner.size > 0) {
            lifecycleScope.launch(Dispatchers.IO) {
                leaderboardsViewModel.setSeason(parent!!.selectedItemPosition)
                withContext(Main) {
                    leaderboardsViewModel.getLeaderboard()
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}