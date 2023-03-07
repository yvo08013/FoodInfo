package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.RecipeAttrsNetwork


interface RecipeAttrAPI {

    fun getRecipeAttrs(): RecipeAttrsNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }
}