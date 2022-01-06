package com.example.taskmanagement.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewMarginItemDecoration(
    private val topMargin: Int,
    private val bottomMargin: Int,
    private val leftMargin: Int,
    private val rightMargin: Int

) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topMargin
            }
            left = leftMargin
            right = rightMargin
            bottom = bottomMargin
        }
    }
}