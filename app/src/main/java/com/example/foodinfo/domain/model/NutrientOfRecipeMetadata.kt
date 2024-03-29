package com.example.foodinfo.domain.model


data class NutrientOfRecipeMetadata(
    val ID: Int,
    val tag: String,
    val name: String,
    val measure: String,
    val description: String,
    val hasRDI: Boolean,
    val previewURL: String,
    val dailyAllowance: Float,
    val precision: Int,
    val stepSize: Float,
    val rangeMin: Float,
    val rangeMax: Float
)