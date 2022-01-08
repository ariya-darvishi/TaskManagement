package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.taskmanagement.R

open class BaseFragment : Fragment() {


     lateinit var navController: NavController
     lateinit var toolbar: Toolbar
     lateinit var toolbarTitle: TextView
     lateinit var toolbarBackBtn: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbarItems(view)

    }

    private fun initToolbarItems(view: View) {
        navController = Navigation.findNavController(view)

        toolbar = view.findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarBackBtn = toolbar.findViewById(R.id.toolbar_back_btn)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    internal fun setupToolbar(onResumeFragmentDestinationId: Int, fragmentToolbarTitle: String) {

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == onResumeFragmentDestinationId) {

                toolbarTitle.text = fragmentToolbarTitle

                toolbarBackBtn.setOnClickListener {
                    Navigation.findNavController(it).popBackStack()
                }
            }

        }

    }

}