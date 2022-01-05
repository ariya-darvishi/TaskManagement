package com.example.taskmanagement.ui.fragments.onMainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.ShowAllTasksRecyclerViewAdapter
import com.example.taskmanagement.databinding.FragmentShowAllTasksBinding
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAllTasksFragment : Fragment() {

    private lateinit var binding: FragmentShowAllTasksBinding
    private val viewModel: MainViewModel by viewModels()

    private val recyclerAdapter = ShowAllTasksRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_all_tasks, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allTasks.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.differ.submitList(it)
            setupRecyclerView()
        })
    }

    private fun setupRecyclerView() {
            binding.showAllTasksRecyclerView.apply {
                adapter = recyclerAdapter
                addItemDecoration(
                    RecyclerViewMarginItemDecoration(20))
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

    }

}