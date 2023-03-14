package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.IngredientOfRecipeDB
import com.example.foodinfo.remote.dto.IngredientOfRecipeNetwork
import com.example.foodinfo.repository.model.RecipeIngredientModel


fun IngredientOfRecipeDB.toModel(): RecipeIngredientModel {
    return RecipeIngredientModel(
        ID = this.ID,
        text = this.text,
        measure = this.measure,
        quantity = this.quantity,
        weight = this.weight,
        food = this.food,
        foodId = this.foodID,
        foodCategory = this.foodCategory,
        previewURL = this.previewURL
    )
}

fun IngredientOfRecipeNetwork.toDB(recipeID: String): IngredientOfRecipeDB {
    return IngredientOfRecipeDB(
        recipeID = recipeID,
        text = this.text,
        quantity = this.quantity,
        measure = this.measure,
        weight = this.weight,
        food = this.food,
        foodCategory = this.foodCategory,
        foodID = this.foodID,
        previewURL = this.image,
    )
}