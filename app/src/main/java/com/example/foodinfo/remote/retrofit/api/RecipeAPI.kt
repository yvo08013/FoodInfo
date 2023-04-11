package com.example.foodinfo.remote.retrofit.api

import com.example.foodinfo.remote.model.RecipeHitNetwork
import com.example.foodinfo.remote.model.RecipePageNetwork
import com.example.foodinfo.utils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Url


interface RecipeAPI {

    @GET
    suspend fun getPage(
        @Url
        url: String
    ): ApiResponse<RecipePageNetwork>

    @GET
    suspend fun getRecipe(
        @Url
        url: String
    ): ApiResponse<RecipeHitNetwork>
}