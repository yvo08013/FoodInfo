package com.example.foodinfo.data.remote.retrofit.data_source

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.core.utils.edamam.FieldSet
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.data.remote.model.IngredientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.NutrientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import com.example.foodinfo.data.remote.retrofit.EdamamPageURL
import com.example.foodinfo.data.remote.retrofit.EdamamRecipeURL
import com.example.foodinfo.data.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.SearchFilterPreset
import javax.inject.Inject


class RecipeRetrofitSource @Inject constructor(
    private val recipeAPI: RecipeAPI
) : RecipeRemoteSource {
    override suspend fun getInitPage(
        apiCredentials: EdamamCredentials,
        filterPreset: SearchFilterPreset,
        inputText: String,
    ): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(
            EdamamPageURL.build(apiCredentials, filterPreset, inputText, FieldSet.FULL)
        )
    }

    override suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(href)
    }

    override suspend fun getByIdExtended(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork> {
        return recipeAPI.getRecipe(
            EdamamRecipeURL.build(apiCredentials, FieldSet.FULL, recipeID)
        )
    }

    override suspend fun getByIdNutrients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<Map<String, NutrientOfRecipeNetwork>> {
        return recipeAPI.getNutrientsOfRecipe(
            EdamamRecipeURL.build(apiCredentials, FieldSet.NUTRIENTS, recipeID)
        )
    }

    override suspend fun getByIdIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<List<IngredientOfRecipeNetwork>> {
        return recipeAPI.getIngredientsOfRecipe(
            EdamamRecipeURL.build(apiCredentials, FieldSet.INGREDIENTS, recipeID)
        )
    }
}