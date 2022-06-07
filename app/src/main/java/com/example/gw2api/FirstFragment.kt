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
import kotlinx.coroutines.Dispatchers.IO
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
            leaderboardAdapter.setListData(leaderboardsViewModel.leaderboardLiveData.value!!)
            leaderboardAdapter.notifyDataSetChanged()
        }
        val leaderboardDetailsObserver = Observer<List<String>> {
        }
        val leaderboardListObserver = Observer<SeasonList> {
        }
        leaderboardsViewModel.leaderboardLiveData.observe(viewLifecycleOwner, leaderboardObserver)
        leaderboardsViewModel.seasonNameList.observe(viewLifecycleOwner, leaderboardDetailsObserver)
        leaderboardsViewModel.seasonIdLiveData.observe(viewLifecycleOwner, leaderboardListObserver)

                lifecycleScope.launch(IO) {
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
            leaderboardsViewModel.seasonIdList
        )
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        spinner.setSelection(leaderboardsViewModel.lastSelectedSpinnerPosition.value!!)
    }

    private fun setupRecyclerView() = binding.myRecyclerView.apply {
        leaderboardAdapter = LeaderboardAdapter()
        adapter = leaderboardAdapter
        layoutManager = LinearLayoutManager(context)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (spinner.size > 0) {
            lifecycleScope.launch(Main) {
                leaderboardsViewModel.setSeason(parent!!.selectedItemPosition)
                leaderboardsViewModel.lastSelectedSpinnerPosition.value = position
                withContext(IO) {
                    leaderboardsViewModel.getLeaderboard()
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}