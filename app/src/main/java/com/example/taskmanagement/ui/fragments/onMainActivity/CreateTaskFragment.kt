package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.taskmanagement.R
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.databinding.FragmentCreateTaskBinding
import com.example.taskmanagement.utils.shortSnackBar
import com.example.taskmanagement.viewModels.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    private lateinit var binding: FragmentCreateTaskBinding
    private val viewModel: MainViewModel by viewModels()

    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var toolbarBackBtn: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false)

        binding.fragmentCreateTask = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setupToolbar(view: View) {

        navController = Navigation.findNavController(view)

        toolbar = view.findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarBackBtn = toolbar.findViewById(R.id.toolbar_back_btn)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.createTaskFragment) {

                toolbarTitle.text = "Create Task"

                toolbarBackBtn.setOnClickListener {
                    Navigation.findNavController(it).popBackStack()
                }
            }

        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun createNewTaskBtnClickListener(view: View) {

        binding.apply {
            checkEmptyAllTextFields()
            if (this.createTaskInputTitle.text.isNullOrBlank()) {
                changeBackgroundLayoutToDangerousLayout(this.createTaskInputTitle)
                view.shortSnackBar("Pleas Enter Task Title...")
            } else {
                clearDangerBackground(this.createTaskInputTitle)
                if (this.createTaskInputShortDescription.text.isNullOrBlank()) {
                    changeBackgroundLayoutToDangerousLayout(this.createTaskInputShortDescription)
                    view.shortSnackBar("Pleas Enter Task Short Description...")
                } else {
                    clearDangerBackground(this.createTaskInputShortDescription)
                    if (this.taskDescription.text.isNullOrBlank()) {
                        changeBackgroundLayoutToDangerousLayout(this.taskDescription)
                        view.shortSnackBar("Pleas Enter Task Description...")
                    } else {
                        clearDangerBackground(this.taskDescription)

                        insertTaskToDatabase(view)

                        clearUserInputText()
                    }
                }
            }
        }

    }

    private fun checkEmptyAllTextFields() {
        binding.apply {
            if (
                this.createTaskInputTitle.text.isNullOrBlank()
                && this.createTaskInputTitle.text.isNullOrBlank()
                && this.createTaskInputTitle.text.isNullOrBlank()
            ) {
                changeBackgroundLayoutToDangerousLayout(this.createTaskInputTitle)
                changeBackgroundLayoutToDangerousLayout(this.createTaskInputTitle)
                changeBackgroundLayoutToDangerousLayout(this.createTaskInputTitle)
            }
        }
    }

    private fun insertTaskToDatabase(view: View) {

        val taskTitle = binding.createTaskInputTitle.text.toString()
        val taskShortDescription = binding.createTaskInputShortDescription.text.toString()
        val taskDescription = binding.taskDescription.text.toString()

        val newTask = Task(
            0,
            taskTitle,
            taskShortDescription,
            taskDescription,
            ""
        )

        viewModel.insertTask(newTask)

        view.shortSnackBar("Successfully Create your task!")
    }

    private fun clearUserInputText() {
        binding.createTaskInputTitle.text?.clear()
        binding.createTaskInputShortDescription.text?.clear()
        binding.taskDescription.text?.clear()

    }

    private fun clearDangerBackground(view: TextInputEditText) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.gray_bg
        )
    }

    private fun changeBackgroundLayoutToDangerousLayout(view: TextInputEditText) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.text_input_edit_text_danger_bg
        )
    }
}

