package com.example.taskmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.data.entities.Task
import android.annotation.SuppressLint
import android.view.View
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.RecyclerViewItemShowAllTasksMinusNewestTaskBinding
import com.timqi.sectorprogressview.ColorfulRingProgressView
import androidx.recyclerview.widget.LinearLayoutManager

import android.R.attr.name
import android.widget.LinearLayout
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.RecyclerViewOverlapDecoration
import com.example.taskmanagement.utils.setupRecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.properties.Delegates


class ShowAllTasksMinusNewestTaskRecyclerViewAdapter() :
    RecyclerView.Adapter<ShowAllTasksMinusNewestTaskRecyclerViewAdapter.ShowTasksViewHolder>() {

    inner class ShowTasksViewHolder(binding: RecyclerViewItemShowAllTasksMinusNewestTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nestedRecyclerView: RecyclerView =
            itemView.findViewById<RecyclerView>(R.id.nested_users_recycler_view)
        val memberNumber: TextView = itemView.findViewById<TextView>(R.id.show_users_number)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var dataList: List<Task>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var userList: List<TaskMember>? = mutableListOf<TaskMember>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(newData: List<TaskMember>) {
        userList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowTasksViewHolder {
        val recyclerViewItemShowAllTasks: RecyclerViewItemShowAllTasksMinusNewestTaskBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item_show_all_tasks_minus_newest_task,
                parent,
                false
            )
        return ShowTasksViewHolder(recyclerViewItemShowAllTasks)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    @SuppressLint("SetTextI18n", "CutPasteId", "WrongConstant")
    override fun onBindViewHolder(
        holder: ShowTasksViewHolder,
        position: Int
    ) {

        val taskItem = dataList[position]

        holder.itemView.apply {

            holder.itemView.findViewById<TextView>(R.id.task_title).text =
                taskItem.taskTitle
            holder.itemView.findViewById<TextView>(R.id.short_description).text =
                taskItem.shortDescription

            if (!taskItem.task_progression.equals(0f)) {
                holder.itemView.findViewById<ColorfulRingProgressView>(R.id.work_progression_in_home_fragment)
                    .apply {
                        visibility = View.VISIBLE
                        percent = taskItem.task_progression!!
                    }
                holder.itemView.findViewById<TextView>(R.id.work_progression).apply {
                    visibility = View.VISIBLE
                    text = "${taskItem.task_progression}%"
                }
            }else{

                holder.itemView.findViewById<ColorfulRingProgressView>(R.id.work_progression_in_home_fragment)
                    .apply {
                        visibility = View.GONE
                    }
                holder.itemView.findViewById<TextView>(R.id.work_progression).apply {
                    visibility = View.GONE
                }
            }

            setOnClickListener {
                onItemClickListener?.let {
                    it(taskItem)
                }
            }

        }


        var users: MutableList<TaskMember> = mutableListOf()

        userList?.forEach { user ->
            if (user.taskId == taskItem.taskId) {
                users.add(user)
            }
        }

//        if (taskItem.task_progression.equals(0f) ) {
//
//            holder.itemView.findViewById<ColorfulRingProgressView>(R.id.work_progression_in_home_fragment)
//                .apply {
//                    visibility = View.GONE
//                }
//            holder.itemView.findViewById<TextView>(R.id.work_progression).apply {
//                visibility = View.GONE
//            }
//        }

        val itemRecyclerAdapter = TaskMembersRecyclerViewAdapter()
        itemRecyclerAdapter.differ.submitList(users)

        holder.nestedRecyclerView.setupRecyclerView(
            LinearLayoutManager(holder.nestedRecyclerView.context, LinearLayout.HORIZONTAL, false),
            itemRecyclerAdapter,
            RecyclerViewOverlapDecoration(0, 0, 0, -30)
        )
        holder.memberNumber.apply {
            this.text = "${users.count()} participants"
        }
//        apply {
//            layoutManager = LinearLayoutManager(holder.nestedRecyclerView.context, LinearLayout.HORIZONTAL, false)
//            val itemRecyclerAdapter = TaskMembersRecyclerViewAdapter()
//            itemRecyclerAdapter.differ.submitList(userList)
//            adapter = itemRecyclerAdapter
//
//        }

    }

    private var onItemClickListener: ((Task) -> Unit)? = null

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }


//    private fun setCatItemRecycler(
//        recyclerView: RecyclerView,
//        userList: List<User>
//    ) {
//        val itemRecyclerAdapter = TaskMembersRecyclerViewAdapter()
//        itemRecyclerAdapter.differ.submitList(userList)
//        recyclerView.adapter = itemRecyclerAdapter
//        recyclerView.layoutManager =
//            LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
//
//    }
}