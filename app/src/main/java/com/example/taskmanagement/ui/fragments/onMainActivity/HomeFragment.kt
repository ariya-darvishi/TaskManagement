package com.example.taskmanagement.ui.fragments.onMainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentHomeBinding
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()


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


    }

    private fun showNewestTask() {
        viewModel.newestTask.observe(viewLifecycleOwner, Observer {
            if (it!= null) {
                binding.newestTaskTitle.text = it.taskTitle
                binding.newestTaskShortDescription.text = it.shortDescription
            }
        })
    }

    fun onSeeAllTasksClickListener(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_showAllTasksFragment)
    }

}