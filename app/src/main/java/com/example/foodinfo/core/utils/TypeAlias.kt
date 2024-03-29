package com.example.foodinfo.core.utils

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.data.remote.NetworkResponse


typealias ApiResponse<S> = NetworkResponse<S, Any>

typealias AppListAdapter<T> = ListAdapter<T, ViewHolder>
typealias AppPageAdapter<T> = PagingDataAdapter<T, ViewHolder>