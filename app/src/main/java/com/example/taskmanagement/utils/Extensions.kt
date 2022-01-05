package com.example.taskmanagement.utils

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.adapters.ShowAllTasksRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar

fun View.shortSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackBar.it() }
    snackBar.show()
}

internal fun View.longSnackBar(message: String, action: (Snackbar.() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackBar.it() }
    snackBar.show()
}

fun RecyclerView.setupRecyclerView(
    linearLayoutManager: LinearLayoutManager,
    recyclerViewAdapter: RecyclerView.Adapter<*>,
    recyclerItemDecoration: RecyclerView.ItemDecoration
) {
    this.apply {
        layoutManager = linearLayoutManager
        adapter = recyclerViewAdapter
        addItemDecoration(recyclerItemDecoration)
        setHasFixedSize(true)
    }
}