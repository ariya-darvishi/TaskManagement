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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.ShowAllTasksMinusNewestTaskRecyclerViewAdapter
import com.example.taskmanagement.adapters.TaskMembersRecyclerViewAdapter
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.FragmentHomeBinding
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.setupRecyclerView
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private var showAllTasksMinusNewestTaskRecyclerViewAdapter =
        ShowAllTasksMinusNewestTaskRecyclerViewAdapter()
    private val taskMembersRecyclerAdapter = TaskMembersRecyclerViewAdapter()
    private var newestTask: Task? = null
    private var users = listOf<User>()
    private var taskMembers = listOf<TaskMember>()

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

        getAllMember()
        getAllUsers()

        setOnItemClickListenerForRecyclerView()

    }

    private fun setOnItemClickListenerForRecyclerView() {
        showAllTasksMinusNewestTaskRecyclerViewAdapter.setOnItemClickListener {
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

            showAllTasksMinusNewestTaskRecyclerViewAdapter.differ.submitList(it)
            showAllTasksMinusNewestTaskRecyclerViewAdapter.setUserList(taskMembers)
            initShowAllTasksMinusNewestTaskRecyclerView()

        })
    }

    private fun initShowAllTasksMinusNewestTaskRecyclerView() {
        binding.allTasksMinusNewestTaskRecyclerView.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            showAllTasksMinusNewestTaskRecyclerViewAdapter,
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
                initTaskMembers(it.taskId)
            } else {
                binding.newestTaskMainLayout.visibility = View.GONE
            }
        })
    }


    private fun initTaskMembers(taskId: Int) {
        viewModel.getTaskWithTaskMembers(taskId).observe(viewLifecycleOwner, Observer {
            it.forEach {
                taskMembersRecyclerAdapter.differ.submitList(it.taskMembers)
                initMembersRecyclerView()

            }

        })
    }

    private fun initMembersRecyclerView() {
//        binding.newestTaskUsersRecyclerView.setupRecyclerView(
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
//            taskMembersRecyclerAdapter,
//            RecyclerViewMarginItemDecoration(0, -30, 0, 0)
//        )
        binding.newestTaskUsersRecyclerView.apply {
            adapter = taskMembersRecyclerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(RecyclerViewMarginItemDecoration(1, 1, 1, -30))
        }
    }

    fun onSeeAllTasksClickListener(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_showAllTasksFragment)
    }

    fun onDetailsNewestTaskClickListener(view: View) {
        val bundle = Bundle().apply {
            putParcelable("task", newestTask)
        }
        view.findNavController().navigate(
            R.id.action_homeFragment_to_taskDetailFragment,
            bundle
        )
    }

    fun onAddMemberClickListener() {
        AddMemberDialog(
            users,
            requireContext(),
            object : AddMemberToTaskDialogListener {
                override fun onAddButtonClicked(usersList: List<User>) {
                    setCurrentTaskIdForAddedUsersAndUpdateUsers(usersList)
                }
            }).show()
    }


    private fun setCurrentTaskIdForAddedUsersAndUpdateUsers(membersList: List<User>) {
        membersList.forEach { user ->
            val taskMember = TaskMember(
                0,
                newestTask!!.taskId,
                user
            )
            viewModel.insertTaskMember(taskMember)
        }

    }

    private fun getAllUsers() {
        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            users = it
        })
    }

    private fun getAllMember() {
        viewModel.allTaskMembers.observe(viewLifecycleOwner, Observer {
            taskMembers = it
            showNewestTask()
            showAllTasksMinusNewestTask()
        })
    }

}