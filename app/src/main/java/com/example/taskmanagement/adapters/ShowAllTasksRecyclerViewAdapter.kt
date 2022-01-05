package com.example.taskmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.data.entities.Task

class ShowAllTasksRecyclerViewAdapter :
    RecyclerView.Adapter<ShowAllTasksRecyclerViewAdapter.ShowAllTasksViewHolder>() {

    inner class ShowAllTasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowAllTasksRecyclerViewAdapter.ShowAllTasksViewHolder {
        return ShowAllTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_view_item_show_all_tasks,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(
        holder: ShowAllTasksRecyclerViewAdapter.ShowAllTasksViewHolder,
        position: Int
    ) {

        val taskItem = dataList[position]
        holder.itemView.apply {
            holder.itemView.findViewById<TextView>(R.id.recycler_view_item_show_all_tasks_title_task).text =
                taskItem.taskTitle
            holder.itemView.findViewById<TextView>(R.id.recycler_view_item_show_all_tasks_short_description).text =
                taskItem.shortDescription

            setOnClickListener {
                onItemClickListener?.let {
                    it(taskItem)
                }
            }

        }
    }

    private var onItemClickListener: ((Task) -> Unit)? = null

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }
}