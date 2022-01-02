package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentCreateTaskBinding


class CreateTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateTaskBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false)


        return binding.root
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setupToolbar(view: View) {

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.createTaskFragment) {

                toolbarTitle.text = "Create Task"

                toolbarBackBtn.setOnClickListener {
                    navController.navigate(R.id.homeFragment)
                }
            }

        }

    }

}

