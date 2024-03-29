package com.example.foodinfo.data.local.room.data_source

import androidx.paging.PagingSource
import com.example.foodinfo.data.local.data_source.RecipeLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.RoomPageQuery
import com.example.foodinfo.data.local.room.dao.RecipeDAO
import com.example.foodinfo.data.local.room.model.entity.IngredientOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.RecipeEntity
import com.example.foodinfo.domain.model.SearchFilterPreset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRoomSource @Inject constructor(
    private val recipeDAO: RecipeDAO
) : RecipeLocalSource {
    override fun getFavorite(): PagingSource<Int, out RecipeDB> {
        return recipeDAO.getFavorite()
    }

    override fun getByFilter(
        filterPreset: SearchFilterPreset,
        inputText: String,
        sessionStartTime: Long
    ): PagingSource<Int, out RecipeDB> {
        return recipeDAO.getByFilter(RoomPageQuery.build(filterPreset, inputText, sessionStartTime))
    }

    override fun getByIdExtended(recipeID: String): Flow<RecipeExtendedDB> {
        return recipeDAO.getByIdExtended(recipeID)
    }

    override fun getIngredients(recipeID: String): Flow<List<IngredientOfRecipeDB>> {
        return recipeDAO.getIngredients(recipeID)
    }

    override fun getNutrients(recipeID: String): Flow<List<NutrientOfRecipeExtendedDB>> {
        return recipeDAO.getNutrients(recipeID)
    }

    override fun getLabels(recipeID: String): Flow<List<LabelOfRecipeExtendedDB>> {
        return recipeDAO.getLabels(recipeID)
    }

    override fun getFavoriteCount(): Flow<Int> {
        return recipeDAO.getFavoriteCount()
    }

    override suspend fun getFavoriteIds(): List<String> {
        return recipeDAO.getFavoriteIds()
    }

    override suspend fun invertFavoriteStatus(recipeID: String) {
        recipeDAO.invertFavoriteStatus(recipeID)
    }

    override suspend fun delFromFavorite(recipeIDs: List<String>) {
        recipeDAO.delFromFavorite(recipeIDs)
    }

    override suspend fun addRecipes(recipes: List<RecipeToSaveDB>) {
        recipeDAO.addRecipes(
            recipes = recipes.map(RecipeEntity::invoke),
            labels = recipes.flatMap { it.labels }.map(LabelOfRecipeEntity::invoke),
            nutrients = recipes.flatMap { it.nutrients }.map(NutrientOfRecipeEntity::invoke),
            ingredients = recipes.flatMap { it.ingredients }.map(IngredientOfRecipeEntity::invoke)
        )
    }

    override suspend fun addRecipe(recipe: RecipeToSaveDB) {
        recipeDAO.addRecipe(
            recipe = RecipeEntity(recipe),
            labels = recipe.labels.map(LabelOfRecipeEntity::invoke),
            nutrients = recipe.nutrients.map(NutrientOfRecipeEntity::invoke),
            ingredients = recipe.ingredients.map(IngredientOfRecipeEntity::invoke)
        )
    }

    override suspend fun addLabels(labels: List<LabelOfRecipeDB>) {
        recipeDAO.addLabels(labels.map(LabelOfRecipeEntity::invoke))
    }

    override suspend fun addNutrients(nutrients: List<NutrientOfRecipeDB>) {
        recipeDAO.addNutrients(nutrients.map(NutrientOfRecipeEntity::invoke))
    }

    override suspend fun addIngredients(ingredients: List<IngredientOfRecipeDB>) {
        recipeDAO.addIngredients(ingredients.map(IngredientOfRecipeEntity::invoke))
    }
}