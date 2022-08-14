package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.entity.RecipeNutrientEntity
import com.example.foodinfo.repository.model.RecipeNutrientModel


fun RecipeNutrientEntity.toModel(): RecipeNutrientModel {
    return RecipeNutrientModel(
        id = this.id,
        label = this.label,
        measure = this.unit,
        totalWeight = this.totalValue,
        dailyWeight = (this.totalValue * 100) / this.dailyValue,
        dailyPercent = this.dailyValue.toInt()
    )
}