package com.example.foodinfo.repository.model.filter_field


data class NutrientField(
    val value: String,
    val minValue: Double? = null,
    val maxValue: Double? = null
)