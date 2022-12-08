package com.example.foodinfo.ui.custom_view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager


class NonScrollLinearLayoutManager(context: Context?) :
    LinearLayoutManager(context) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}