package com.example.taskmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.databinding.RecyclerViewItemSubTaskBinding


class SubTaskRecyclerViewAdapter() :
    RecyclerView.Adapter<SubTaskRecyclerViewAdapter.ShowSubTasksViewHolder>() {

    class ShowSubTasksViewHolder(binding: RecyclerViewItemSubTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<SubTask>() {
        override fun areItemsTheSame(
            oldItem: SubTask,
            newItem: SubTask
        ): Boolean {
            return oldItem.subTaskId == newItem.subTaskId
        }

        override fun areContentsTheSame(
            oldItem: SubTask,
            newItem: SubTask
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var dataList: List<SubTask>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<SubTask>) {
        dataList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubTaskRecyclerViewAdapter.ShowSubTasksViewHolder {
        val recyclerViewItemShowAllTasks: RecyclerViewItemSubTaskBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item_sub_task,
                parent,
                false
            )
        return ShowSubTasksViewHolder(recyclerViewItemShowAllTasks)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(
        holder: ShowSubTasksViewHolder,
        position: Int
    ) {
        val completedIcon =
            holder.itemView.findViewById<ImageView>(R.id.task_completed_btn)
            holder.itemView.findViewById<TextView>(R.id.sub_task_title)
        val subTaskTitle =
            holder.itemView.findViewById<TextView>(R.id.sub_task_title)
        val subTaskPendingOrComplete =
            holder.itemView.findViewById<TextView>(R.id.pending_or_complete_text_view)
        val subTaskItemLayout =
            holder.itemView.findViewById<ConstraintLayout>(R.id.recycler_item_sub_task_main_layout)


        val subTaskItem = dataList[position]
        holder.itemView.apply {
            subTaskTitle.text = subTaskItem.subTaskTitle

            if (subTaskItem.isCompleted) {
                isSelected = true
                subTaskItemLayout.setBackgroundResource(
                    R.drawable.sub_task_outline_blue
                )
                completedIcon?.visibility = View.VISIBLE
                subTaskPendingOrComplete.apply {
                    text = "(complete)"
                    setTextColor(resources.getColor(R.color.blue_color))
                }
            } else {
                isSelected = false
                subTaskPendingOrComplete.apply {
                    text = "(Pending)"
                    setTextColor(resources.getColor(R.color.orange_color))
                }
            }

            setOnClickListener {
                onItemClickListener?.let {

                    it(subTaskItem)
                }
            }

        }
    }


    private var onItemClickListener: ((SubTask) -> Unit)? = null

    fun setOnItemClickListener(listener: (SubTask) -> Unit) {
        onItemClickListener = listener
    }
}