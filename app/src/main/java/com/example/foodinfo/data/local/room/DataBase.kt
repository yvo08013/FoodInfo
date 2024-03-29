package com.example.foodinfo.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodinfo.data.local.room.dao.*
import com.example.foodinfo.data.local.room.model.entity.*


@Database(
    exportSchema = false,
    version = 1,
    entities = [
        RecipeEntity::class,
        SearchFilterEntity::class,
        SearchInputEntity::class,
        IngredientOfRecipeEntity::class,
        NutrientOfRecipeEntity::class,
        LabelOfRecipeEntity::class,
        LabelOfRecipeMetadataEntity::class,
        CategoryOfRecipeMetadataEntity::class,
        LabelOfSearchFilterEntity::class,
        NutrientOfSearchFilterEntity::class,
        NutrientOfRecipeMetadataEntity::class,
        BasicOfRecipeMetadataEntity::class,
        BasicOfSearchFilterEntity::class,
        EdamamCredentialsEntity::class,
        GitHubCredentialsEntity::class,
    ]
)
abstract class DataBase : RoomDatabase() {
    abstract val recipeDAO: RecipeDAO
    abstract val searchFilterDAO: SearchFilterDAO
    abstract val searchHistoryDAO: SearchHistoryDAO
    abstract val recipeMetadataDAO: RecipeMetadataDAO
    abstract val apiCredentialsDAO: APICredentialsDAO

    companion object {
        private const val DB_NAME = "data_base"

        private lateinit var dataBase: DataBase

        fun getDatabase(
            context: Context,
        ): DataBase {
            if (!Companion::dataBase.isInitialized)
                dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    DB_NAME
                ).build()
            return dataBase
        }
    }
}