package com.example.taskmanagement.ui.fragments.onMainActivity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.AddMemberToTaskRecyclerViewAdapter
import com.example.taskmanagement.data.entities.User
import com.google.android.material.button.MaterialButton


class AddMemberDialog(
    private var usersList: List<User>,
    context: Context,
    var addMemberDialogListener: AddMemberToTaskDialogListener
) :
    AppCompatDialog(context) {

    private val recyclerAdapter = AddMemberToTaskRecyclerViewAdapter()
//    private var membersList:List<User> = mutableListOf()
    private var membersList:MutableList<User> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_member)
        window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

        initRecyclerView()

        setOnItemClickListenerForRecyclerView()

        onAddBtnClickListener()
        onCancelBtnClickListener()


    }

    private fun onAddBtnClickListener() {
        findViewById<MaterialButton>(R.id.add_member_btn)?.setOnClickListener {
            addMemberDialogListener.onAddButtonClicked(membersList.toList())
            dismiss()
        }
    }

    private fun onCancelBtnClickListener() {
        findViewById<MaterialButton>(R.id.cancel_btn)?.setOnClickListener {
            cancel()
        }
    }

    private fun setOnItemClickListenerForRecyclerView() {
        recyclerAdapter.setOnItemClickListener {
            if (!membersList.contains(it)) {
                membersList.add(it)
            } else {
                membersList.remove(it)
            }

        }
    }

    private fun initRecyclerView() {
        recyclerAdapter.differ.submitList(usersList)
        findViewById<RecyclerView>(R.id.users_list_recycler_view)?.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            RecyclerViewMarginItemDecoration(0, 0, 0, 0)
        }
    }


}