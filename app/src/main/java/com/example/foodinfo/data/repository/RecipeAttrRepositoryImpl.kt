package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.RecipeAttrLocalSource
import com.example.foodinfo.data.local.model.CategoryRecipeAttrDB
import com.example.foodinfo.data.local.model.LabelRecipeAttrDB
import com.example.foodinfo.data.local.model.NutrientRecipeAttrDB
import com.example.foodinfo.data.local.model.RecipeAttrsDB
import com.example.foodinfo.data.mapper.toDB
import com.example.foodinfo.data.mapper.toModel
import com.example.foodinfo.data.mapper.toModelHint
import com.example.foodinfo.data.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.data.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class RecipeAttrRepositoryImpl @Inject constructor(
    private val recipeAttrLocal: RecipeAttrLocalSource,
    private val recipeAttrRemote: RecipeAttrRemoteSource
) : BaseRepository(), RecipeAttrRepository {

    override suspend fun getNutrientHint(ID: Int): NutrientHint {
        return recipeAttrLocal.getNutrient(ID).toModelHint()
    }

    override suspend fun getLabelHint(ID: Int): LabelHint {
        return recipeAttrLocal.getLabel(ID).toModelHint()
    }

    override fun getRecipeAttrs(apiCredentials: GitHubCredentials): Flow<State<RecipeAttrs>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeAttrRemote.getRecipeAttrs(apiCredentials)) },
            localDataProvider = {
                DataSource.LocalFlow(
                    combine(
                        recipeAttrLocal.observeBasics(),
                        recipeAttrLocal.observeLabels(),
                        recipeAttrLocal.observeNutrients(),
                        recipeAttrLocal.observeCategories(),
                        ::RecipeAttrsDB
                    )
                )
            },
            saveRemoteDelegate = recipeAttrLocal::addRecipeAttrs,
            mapToLocalDelegate = RecipeAttrsNetwork::toDB,
            mapToModelDelegate = RecipeAttrsDB::toModel
        )
    }

    override fun getLabels(apiCredentials: GitHubCredentials): Flow<State<List<LabelRecipeAttr>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeAttrRemote.getRecipeAttrs(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeAttrLocal.observeLabels()) },
            saveRemoteDelegate = recipeAttrLocal::addRecipeAttrs,
            mapToLocalDelegate = RecipeAttrsNetwork::toDB,
            mapToModelDelegate = { labels -> labels.map(LabelRecipeAttrDB::toModel) }
        )
    }

    override fun getNutrients(apiCredentials: GitHubCredentials): Flow<State<List<NutrientRecipeAttr>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeAttrRemote.getRecipeAttrs(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeAttrLocal.observeNutrients()) },
            saveRemoteDelegate = recipeAttrLocal::addRecipeAttrs,
            mapToLocalDelegate = RecipeAttrsNetwork::toDB,
            mapToModelDelegate = { nutrients -> nutrients.map(NutrientRecipeAttrDB::toModel) }
        )
    }

    override fun getCategories(apiCredentials: GitHubCredentials): Flow<State<List<CategoryRecipeAttr>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeAttrRemote.getRecipeAttrs(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeAttrLocal.observeCategories()) },
            saveRemoteDelegate = recipeAttrLocal::addRecipeAttrs,
            mapToLocalDelegate = RecipeAttrsNetwork::toDB,
            mapToModelDelegate = { categories -> categories.map(CategoryRecipeAttrDB::toModel) }
        )
    }

    override fun getCategoryLabels(
        apiCredentials: GitHubCredentials,
        categoryID: Int
    ): Flow<State<List<LabelRecipeAttr>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeAttrRemote.getRecipeAttrs(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeAttrLocal.observeCategoryLabels(categoryID)) },
            saveRemoteDelegate = recipeAttrLocal::addRecipeAttrs,
            mapToLocalDelegate = RecipeAttrsNetwork::toDB,
            mapToModelDelegate = { labels -> labels.map(LabelRecipeAttrDB::toModel) }
        )
    }
}