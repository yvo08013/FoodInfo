package com.example.foodinfo.core.utils.edamam

import com.example.foodinfo.core.utils.extensions.trimMultiline
import com.example.foodinfo.data.remote.model.RecipeNetwork
import com.example.foodinfo.data.remote.retrofit.EdamamPageURL
import com.example.foodinfo.data.remote.retrofit.EdamamRecipeURL


object EdamamInfo {
    const val PAGE_SIZE = 20
    const val THROTTLING_CALLS = 10
    const val THROTTLING_TIMER = 60
    const val RECIPE_TYPE_FIELD = "type"
    const val RECIPE_TYPE_PUBLIC = "public"
    const val RECIPE_TYPE_USER = "user"
    const val RECIPE_TYPE_ANY = "any"
    const val APP_KEY_FIELD = "app_key"
    const val APP_ID_FIELD = "app_id"
    const val IMAGE_TOKEN_EXPIRATION_TIME = 3600000L // 1 hour
    const val IMAGE_TOKEN_FIELD = "X-Amz-Date="
}

/**
 * Enum of Recipe fields that will tell [EdamamRecipeURL] and [EdamamPageURL] which fields should be included
 * in response to filter out unnecessary content and reduce network usage.
 */
enum class FieldSet(val fields: String) {
    FULL(
        """
        &field=${RecipeNetwork.Fields.URI}
        &field=${RecipeNetwork.Fields.URL}
        &field=${RecipeNetwork.Fields.SOURCE}
        &field=${RecipeNetwork.Fields.LABEL}
        &field=${RecipeNetwork.Fields.IMAGE}
        &field=${RecipeNetwork.Fields.SERVINGS}
        &field=${RecipeNetwork.Fields.CALORIES}
        &field=${RecipeNetwork.Fields.WEIGHT}
        &field=${RecipeNetwork.Fields.TIME}
        &field=${RecipeNetwork.Fields.MEAL}
        &field=${RecipeNetwork.Fields.DISH}
        &field=${RecipeNetwork.Fields.DIET}
        &field=${RecipeNetwork.Fields.HEALTH}
        &field=${RecipeNetwork.Fields.CUISINE}
        &field=${RecipeNetwork.Fields.INGREDIENTS}
        &field=${RecipeNetwork.Fields.TOTAL_NUTRIENTS}
        """.trimMultiline()
    ),
    BASIC(
        """
        &field=${RecipeNetwork.Fields.URI}
        &field=${RecipeNetwork.Fields.URL}
        &field=${RecipeNetwork.Fields.SOURCE}
        &field=${RecipeNetwork.Fields.LABEL}
        &field=${RecipeNetwork.Fields.IMAGE}
        &field=${RecipeNetwork.Fields.SERVINGS}
        &field=${RecipeNetwork.Fields.CALORIES}
        &field=${RecipeNetwork.Fields.WEIGHT}
        &field=${RecipeNetwork.Fields.TIME}
        """.trimMultiline()
    ),
    NUTRIENTS(
        """
        &field=${RecipeNetwork.Fields.TOTAL_NUTRIENTS}
        """.trimMultiline()
    ),
    INGREDIENTS(
        """
        &field=${RecipeNetwork.Fields.INGREDIENTS}
        """.trimMultiline()
    )
}
