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
import com.example.taskmanagement.databinding.RecyclerViewItemShowAllTasksBinding
import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.RecyclerViewItemTaskMemberBinding
import com.google.android.material.imageview.ShapeableImageView
import javax.inject.Inject


class TaskMembersRecyclerViewAdapter :
    RecyclerView.Adapter<TaskMembersRecyclerViewAdapter.TaskMembersViewHolder>() {

    class TaskMembersViewHolder(binding: RecyclerViewItemTaskMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<TaskMember>() {
        override fun areItemsTheSame(
            oldItem: TaskMember,
            newItem: TaskMember
        ): Boolean {
            return oldItem.memberId == newItem.memberId
        }

        override fun areContentsTheSame(
            oldItem: TaskMember,
            newItem: TaskMember
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var dataList: List<TaskMember>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<TaskMember>) {
        dataList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskMembersRecyclerViewAdapter.TaskMembersViewHolder {
        val recyclerViewItemTaskMembers: RecyclerViewItemTaskMemberBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item_task_member,
                parent,
                false
            )
        return TaskMembersViewHolder(recyclerViewItemTaskMembers)
    }

    override fun getItemCount(): Int {
//        return dataList.size
        return dataList.size
    }

    override fun onBindViewHolder(
        holder: TaskMembersRecyclerViewAdapter.TaskMembersViewHolder,
        position: Int
    ) {

        val memberItem = dataList[position]
        holder.itemView.apply {
            Glide.with(this).load(memberItem.user.userImg).into(this.findViewById<ShapeableImageView>(R.id.user_image))
            setOnClickListener {
                onItemClickListener?.let {
                    it(memberItem)
                }
            }

        }
    }

    private var onItemClickListener: ((TaskMember) -> Unit)? = null

    fun setOnItemClickListener(listener: (TaskMember) -> Unit) {
        onItemClickListener = listener
    }
}