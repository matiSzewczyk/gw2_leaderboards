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

    private lateinit var seasonSpinner: Spinner
    private lateinit var regionSpinner: Spinner
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
        val leaderboardDetailsObserver = Observer<List<String>> {}
        val leaderboardListObserver = Observer<SeasonList> {}

        leaderboardsViewModel.leaderboardLiveData.observe(viewLifecycleOwner, leaderboardObserver)
        leaderboardsViewModel.seasonNameList.observe(viewLifecycleOwner, leaderboardDetailsObserver)
        leaderboardsViewModel.seasonIdLiveData.observe(viewLifecycleOwner, leaderboardListObserver)

        lifecycleScope.launch(IO) {
            leaderboardsViewModel.getSeasonList()
            withContext(Main) {
                leaderboardsViewModel.getSeasonName()
                setupSpinner()
            }
        }
    }

    private fun setupSpinner() {
        seasonSpinner = binding.seasonSpinner
        val seasonAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            leaderboardsViewModel.seasonIdList
        )
        seasonSpinner.adapter = seasonAdapter
        seasonSpinner.onItemSelectedListener = this
        seasonSpinner.setSelection(leaderboardsViewModel.lastSelectedSpinnerPosition.value!!)

        regionSpinner = binding.regionSpinner
        val regionAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            leaderboardsViewModel.regionList
        )
        regionSpinner.adapter = regionAdapter
        regionSpinner.onItemSelectedListener = this
    }

    private fun setupRecyclerView() = binding.myRecyclerView.apply {
        leaderboardAdapter = LeaderboardAdapter()
        adapter = leaderboardAdapter
        layoutManager = LinearLayoutManager(context)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.region_spinner -> {
                leaderboardsViewModel.setRegion(parent.selectedItemPosition)
                // I can't use this since it will call getLeaderboard() twice.
//                if (seasonSpinner.size > 0) {
//                    lifecycleScope.launch(Main) {
//                        leaderboardsViewModel.setSeasonId(leaderboardsViewModel.lastSelectedSpinnerPosition.value!!)
//                        leaderboardsViewModel.lastSelectedSpinnerPosition.value = position
//                        withContext(IO) {
//                            leaderboardsViewModel.getLeaderboard()
//                        }
//                    }
//                }
            }
            R.id.season_spinner -> {
                if (seasonSpinner.size > 0) {
                    lifecycleScope.launch(Main) {
                        leaderboardsViewModel.setSeasonId(parent.selectedItemPosition)
                        leaderboardsViewModel.lastSelectedSpinnerPosition.value = position
                        withContext(IO) {
                            leaderboardsViewModel.getLeaderboard()
                        }
                    }
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}