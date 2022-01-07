package com.example.taskmanagement.ui.fragments.onMainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.ShowAllTasksMinusNewestTaskRecyclerViewAdapter
import com.example.taskmanagement.adapters.ShowAllTasksRecyclerViewAdapter
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.databinding.FragmentHomeBinding
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.setupRecyclerView
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private val recyclerAdapter = ShowAllTasksMinusNewestTaskRecyclerViewAdapter()
    private var newestTask : Task?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.fragmentHome = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNewestTask()
        showAllTasksMinusNewestTask()
        setOnItemClickListenerForRecyclerView()

    }

    private fun setOnItemClickListenerForRecyclerView() {
        recyclerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("task", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_taskDetailFragment,
                bundle
            )
        }
    }

    private fun showAllTasksMinusNewestTask() {
        viewModel.allTasksMinusNewestTask.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty())
                binding.nothingAnyTaskToShowTextView.visibility = View.VISIBLE
            else
                binding.nothingAnyTaskToShowTextView.visibility = View.GONE

            recyclerAdapter.differ.submitList(it)
            initRecyclerView()

        })
    }

    private fun initRecyclerView() {
        binding.allTasksMinusNewestTaskRecyclerView.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            recyclerAdapter,
            RecyclerViewMarginItemDecoration(20, 20, 0, 0)
        )
    }
    private fun showNewestTask() {
        viewModel.newestTask.observe(viewLifecycleOwner, Observer {
            newestTask = it
            if (it != null) {
                binding.newestTaskMainLayout.visibility = View.VISIBLE
                binding.newestTaskTitle.text = it.taskTitle
                binding.newestTaskShortDescription.text = it.shortDescription
            } else {
                binding.newestTaskMainLayout.visibility = View.GONE
            }
        })
    }

    fun onSeeAllTasksClickListener(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_showAllTasksFragment)
    }

    fun onDetailsNewestTaskClickListener(view: View){
        val bundle = Bundle().apply {
            putParcelable("task", newestTask)
        }
        view.findNavController().navigate(
            R.id.action_homeFragment_to_taskDetailFragment,
            bundle
        )
    }

}