package com.example.taskmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.RecyclerViewItemAddMemberToTaskBinding
import com.google.android.material.imageview.ShapeableImageView


class AddMemberToTaskRecyclerViewAdapter :
    RecyclerView.Adapter<AddMemberToTaskRecyclerViewAdapter.UsersViewHolder>() {

    class UsersViewHolder(binding: RecyclerViewItemAddMemberToTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var dataList: List<User>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<User>) {
        dataList = newData
        differ.submitList(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddMemberToTaskRecyclerViewAdapter.UsersViewHolder {
        val recyclerViewItemTaskMembers: RecyclerViewItemAddMemberToTaskBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item_add_member_to_task,
                parent,
                false
            )
        return UsersViewHolder(recyclerViewItemTaskMembers)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private var isSelected: Boolean = false
    private var membersList = mutableSetOf<User>()

    override fun onBindViewHolder(
        holder: AddMemberToTaskRecyclerViewAdapter.UsersViewHolder,
        position: Int
    ) {

        val userItem = dataList[position]
        holder.itemView.apply {
            Glide.with(this).load(userItem.userImg)
                .into(this.findViewById<ShapeableImageView>(R.id.add_member_user_image))
            holder.itemView.findViewById<TextView>(R.id.user_name).text = userItem.userName

            setOnClickListener {
                onItemClickListener?.let {

                    it(userItem)
                    if (!isSelected) {
                        isSelected = true
                        findViewById<ImageView>(R.id.select_user_tick)?.visibility = View.VISIBLE
                    }else {
                        isSelected = false
                        findViewById<ImageView>(R.id.select_user_tick)?.visibility = View.INVISIBLE
                    }

                }
            }

        }
    }

    private var onItemClickListener: ((User) -> Unit)? = null
//    private var onItemClickListener: User? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }
}