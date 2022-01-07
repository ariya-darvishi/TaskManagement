package com.example.taskmanagement.ui.fragments.onMainActivity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.AddMemberToTaskRecyclerViewAdapter
import com.example.taskmanagement.data.entities.User
import com.google.android.material.button.MaterialButton
import java.util.*


class AddMemberDialog(
    var usersList: List<User>,
    context: Context,
    var addMemberDialogListener: AddMemberToTaskDialogListener
) :
    AppCompatDialog(context) {

    private val recyclerAdapter = AddMemberToTaskRecyclerViewAdapter()
    private val membersList = mutableSetOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_member)
        window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

        initRecyclerView()

        setOnItemClickListenerForRecyclerView()

//        onAddBtnClickListener()
        onCancelBtnClickListener()

        findViewById<MaterialButton>(R.id.add_member_btn)?.setOnClickListener {
            addMemberDialogListener.onAddButtonClicked(membersList.toList())
            dismiss()
        }



    }

    private fun onAddBtnClickListener() {

        findViewById<MaterialButton>(R.id.add_member_btn)?.setOnClickListener {
            addMemberDialogListener.onAddButtonClicked(membersList.toList())
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
                Toast.makeText(context, "$membersList", Toast.LENGTH_LONG).show()
            } else {
                membersList.remove(it)
                Toast.makeText(context, "$membersList", Toast.LENGTH_LONG).show()
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