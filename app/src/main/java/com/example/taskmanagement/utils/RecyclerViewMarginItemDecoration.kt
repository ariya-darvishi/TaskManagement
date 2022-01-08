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
//        val childCount = parent.childCount
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        with(outRect) {
//            if (itemPosition == 0) {
                top = topMargin
//            }
//            right = if (itemCount > 0 && itemPosition == itemCount - 1) {
//                0
//            }else{
//                rightMargin
//            }
            right = rightMargin
            left = leftMargin
            bottom = bottomMargin
        }
    }
}