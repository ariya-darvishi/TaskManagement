package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.TaskMembersRecyclerViewAdapter
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.FragmentTaskDetailBinding
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.longSnackBar
import com.example.taskmanagement.utils.setupRecyclerView
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailFragment : BaseFragment() {

    private val recyclerAdapter = TaskMembersRecyclerViewAdapter()
    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: MainViewModel by viewModels()
    private val args: TaskDetailFragmentArgs by navArgs()
    var currentTaskId: Int? = null
    private var users = listOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false)

        binding.fragmentTaskDetail = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
        initTaskDetailsFields()
        getAllUsers()
        showTaskMembers()
    }

    private fun getAllUsers() {
        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            users = it
        })
    }

    private fun initTaskDetailsFields() {
        val task = args.task
        currentTaskId = task.taskId

        binding.taskTitle.text = task.taskTitle
        binding.shortDescription.text = task.shortDescription
        binding.taskDescription.text = task.longDescription
    }

    private fun showTaskMembers() {
        viewModel.getTaskWithUsers(currentTaskId!!).observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it.users.isEmpty()) {
                    binding.notAnyMemberJoined.visibility = View.VISIBLE
                } else {
                    binding.notAnyMemberJoined.visibility = View.INVISIBLE
                    recyclerAdapter.differ.submitList(it.users)
                    initRecyclerView()
                }
            }

        })
    }

    private fun initRecyclerView() {
        binding.taskMembersRecyclerView.visibility = View.VISIBLE
        binding.taskMembersRecyclerView.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            recyclerAdapter,
            RecyclerViewMarginItemDecoration(0, 0, 0, -30)
        )
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setupToolbar(view: View) {

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.taskDetailFragment) {

                toolbarTitle.text = "Task Details"

                toolbarBackBtn.setOnClickListener {
                    Navigation.findNavController(it).popBackStack()
                }
            }

        }

    }

    fun onAddMemberClickListener(view: View) {
        AddMemberDialog(
            users,
            requireContext(),
            object : AddMemberToTaskDialogListener {
                override fun onAddButtonClicked(usersList: List<User>) {
                    addTaskItToMembersAndUpdateUsers(usersList)
                }
            }).show()
    }

    private fun addTaskItToMembersAndUpdateUsers(usersList: List<User>) {
        usersList.forEach { user ->
            user.apply {
                taskId = currentTaskId
            }
            viewModel.updateUser(user)
        }
    }

}