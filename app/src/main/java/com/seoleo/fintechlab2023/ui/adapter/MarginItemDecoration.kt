package com.seoleo.fintechlab2023.ui.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize + 4
                Log.e("TAG", "getItemOffsets: ", )
            }
            left = spaceSize * 2
            right = spaceSize * 2
            bottom = spaceSize + 4
        }
    }
}