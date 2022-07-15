package com.example.foodinfo.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodinfo.model.local.RecipeShort
import com.example.foodinfo.model.local.entities.SearchFilter
import com.example.foodinfo.model.repository.RepositoryRecipes
import com.example.foodinfo.model.repository.RepositorySearchFilter
import javax.inject.Inject

class SearchResultViewModel @Inject constructor(
    private val repositoryRecipes: RepositoryRecipes,
    private val repositorySearchFilter: RepositorySearchFilter
) : ViewModel() {

    private val _recipes: MutableLiveData<List<RecipeShort>> by lazy {
        MutableLiveData<List<RecipeShort>>()
    }
    val recipes: LiveData<List<RecipeShort>>
        get() = _recipes

    fun updateRecipes(header: String) {
        val filter = SearchFilter()
        filter.inputText = header
        filter.buildQuery()
        _recipes.value = repositoryRecipes.getByFilterShort(filter)
    }
}