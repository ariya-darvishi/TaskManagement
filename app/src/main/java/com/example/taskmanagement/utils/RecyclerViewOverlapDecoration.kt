package com.example.taskmanagement.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewOverlapDecoration(
    private val topMargin: Int,
    private val bottomMargin: Int,
    private val leftMargin: Int,
    private val rightMargin: Int

) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
//        if (position == 0) outRect.left = 0 else  outRect.left =rightMargin
//        outRect.right=rightMargin
    }
}