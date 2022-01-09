package com.example.taskmanagement.ui.fragments.onMainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.SubTaskRecyclerViewAdapter
import com.example.taskmanagement.adapters.TaskMembersRecyclerViewAdapter
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.FragmentTaskDetailBinding
import com.example.taskmanagement.ui.activities.MainActivity
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.calculateWorkProgression
import com.example.taskmanagement.utils.setupRecyclerView
import com.example.taskmanagement.utils.shortSnackBar
import com.example.taskmanagement.viewModels.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val taskMembersRecyclerAdapter = TaskMembersRecyclerViewAdapter()
    private val subTaskRecyclerAdapter = SubTaskRecyclerViewAdapter()
    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: MainViewModel by viewModels()
    private val args: TaskDetailFragmentArgs by navArgs()
//    var currentTaskId: Int? = null
    private var percent = 0f
    private var users = listOf<User>()

    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var toolbarBackBtn: ImageButton

    lateinit var mainActivity: MainActivity

    private lateinit var currentTask: Task

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false)

        binding.fragmentTaskDetail = this
        getAllUsers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)

        initTaskDetailsFields()
        setOnSubTaskItemClickListener()

        setupOnBackPressed()
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                updateTask()
                this.remove()
                mainActivity.onBackPressed()
            }

        })
    }


    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setupToolbar(view: View) {

        navController = Navigation.findNavController(view)

        toolbar = view.findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        toolbarBackBtn = toolbar.findViewById(R.id.toolbar_back_btn)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.taskDetailFragment) {

                toolbarTitle.text = "Task Details"

                toolbarBackBtn.setOnClickListener {
                    updateTask()
                    Navigation.findNavController(it).popBackStack()
                }
            }

        }

    }

    private fun initTaskDetailsFields() {
        currentTask = args.task
//        currentTaskId = args.task.taskId


        binding.taskTitle.text = currentTask.taskTitle
        binding.shortDescription.text = currentTask.shortDescription
        binding.taskDescription.text = currentTask.longDescription

        initTaskMembers()
        initSubTasks()
    }

    private fun initTaskMembers() {
        viewModel.getTaskWithTaskMembers(currentTask.taskId).observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it.taskMembers.isEmpty()) {
                    binding.notAnyMemberJoined.visibility = View.VISIBLE
                } else {
                    binding.notAnyMemberJoined.visibility = View.INVISIBLE
                    binding.taskMembersRecyclerView.visibility = View.VISIBLE
                    taskMembersRecyclerAdapter.differ.submitList(it.taskMembers)
                    initMembersRecyclerView()
                }
            }

        })
    }

    private fun initMembersRecyclerView() {
//        binding.taskMembersRecyclerView.setupRecyclerView(
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
//            taskMembersRecyclerAdapter,
//            RecyclerViewMarginItemDecoration(0, 0, 0, 0)
//        )
        binding.taskMembersRecyclerView.apply {
            adapter = taskMembersRecyclerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(RecyclerViewMarginItemDecoration(0, 0, 0, -30))
        }
    }

    private fun initSubTasks() {
        viewModel.getTaskWithSubTasks(currentTask.taskId).observe(viewLifecycleOwner, Observer {
            it.forEach {
                if (it.subTasks.isEmpty()) {
                    binding.notAnyTaskToShow.visibility = View.VISIBLE
                } else {
                    binding.notAnyTaskToShow.visibility = View.INVISIBLE
                    subTaskRecyclerAdapter.differ.submitList(it.subTasks)

                    initSubTaskRecyclerView()
                }
            }

        })
    }

    private fun initSubTaskRecyclerView() {
        binding.subTaskRecyclerView.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            subTaskRecyclerAdapter,
            RecyclerViewMarginItemDecoration(0, 0, 0, 1)
        )
        initWorkProgression()
    }


    private fun getAllUsers() {
        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            users = it
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initWorkProgression() {
        viewModel.getTaskWithSubTasks(currentTask.taskId).observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                it.forEach {
                    val totalSubTask = it.subTasks.count().toFloat()
                    val totalCompleteSubtask = it.subTasks.count {
                        it.isCompleted
                    }.toFloat()
                    showWorkProgression(totalCompleteSubtask, totalSubTask)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showWorkProgression(totalCompleteSubtask: Float, totalSubTask: Float) {
        binding.workProgression.visibility = View.VISIBLE
        binding.workProgressionCircleProgress.visibility = View.VISIBLE


        percent = calculateWorkProgression(totalCompleteSubtask, totalSubTask)
        binding.workProgression.text = "$percent%"
        binding.workProgressionCircleProgress.percent = percent
//        updateTask()

    }

    private fun updateTask() {
        viewModel.updateTask(percent, currentTask.taskId)
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
                currentTask.taskId,
                user
            )
            viewModel.insertTaskMember(taskMember)
        }
    }

    fun onAddSubTaskBtnClickListener(view: View) {
        hideAndroidKeyBoard()
        binding.apply {
            if (this.insertNewSubTaskEditText.text.isNullOrBlank()) {
                changeBackgroundLayoutToDangerousLayout(this.insertNewSubTaskEditText)
                view.shortSnackBar("Pleas Enter Sub Task Title...")
            } else {
                clearDangerBackground(this.insertNewSubTaskEditText)
                insertSubTaskToDatabase()
                initWorkProgression()
            }
        }
    }


    private fun hideAndroidKeyBoard() {
        val androidKeaBoard =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        androidKeaBoard.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun insertSubTaskToDatabase() {

        val inputSubTask = binding.insertNewSubTaskEditText.text.toString()

        val subTask = SubTask(
            0,
            inputSubTask,
            false,
            currentTask.taskId
        )

        viewModel.insertSubTask(subTask)

        binding.insertNewSubTaskEditText.text?.clear()
    }

    private fun changeBackgroundLayoutToDangerousLayout(view: TextInputEditText) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.text_input_edit_text_danger_bg
        )
    }

    private fun clearDangerBackground(view: TextInputEditText) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.gray_bg
        )
    }

    private fun setOnSubTaskItemClickListener() {
        subTaskRecyclerAdapter.setOnItemClickListener {

            when (it.isCompleted) {
                false -> {
                    viewModel.updateSubTaskStatus(it.apply {
                        this.isCompleted = true
                    })
                }
                true -> {
                    viewModel.updateSubTaskStatus(it.apply {
                        this.isCompleted = false
                    })
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        updateTask()
    }
}