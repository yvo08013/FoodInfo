package com.example.foodinfo.data.local.model


open class NutrientOfSearchFilterExtendedDB(
    open val ID: Int,
    open val filterName: String,
    open val infoID: Int,
    open val minValue: Float?,
    open val maxValue: Float?,
    open val metadata: NutrientOfRecipeMetadataDB?
)