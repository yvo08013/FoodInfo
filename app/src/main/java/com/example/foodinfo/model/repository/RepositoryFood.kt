package com.example.foodinfo.model.repository

import com.example.foodinfo.model.local.Food


interface RepositoryFood {
    fun getDaily(): Food
}