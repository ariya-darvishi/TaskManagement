package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.taskmanagement.R
import com.example.taskmanagement.ui.activities.MainActivity


class CreateTaskFragment : Fragment(R.layout.fragment_create_task) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setupToolbar(view: View) {

        val navController = Navigation.findNavController(view)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val toolbarTitle: TextView = toolbar.findViewById(R.id.toolbar_title)
        val toolbarBackBtn: ImageButton = toolbar.findViewById(R.id.toolbar_back_btn)

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

