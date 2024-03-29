package com.example.foodinfo.features.search_filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.core.utils.CoroutineLauncher
import com.example.foodinfo.core.utils.LaunchStrategy
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.features.search_filter.interactor.SearchFilterModelInteractor
import com.example.foodinfo.features.search_filter.model.SearchFilterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchFilterViewModel @Inject constructor(
    private val searchFilterModelInteractor: SearchFilterModelInteractor,
    private val searchFilterRepository: SearchFilterRepository
) : ViewModel() {

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )
    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )


    private val _isFilterDefault = MutableSharedFlow<Boolean>()

    val isFilterDefault: SharedFlow<Boolean> = _isFilterDefault.asSharedFlow()


    val filter: SharedFlow<State<SearchFilterModel>> by lazy {
        searchFilterModelInteractor.getFilterEdit().transform { state ->
            state.data?.let { filter ->
                _isFilterDefault.emit(checkIsDefault(filter))
            }
            emit(state)
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)
    }

    private fun checkIsDefault(filter: SearchFilterModel) =
        filter.nutrients.isEmpty() &&
        filter.basics.all { it.minValue == null && it.maxValue == null } &&
        filter.categories.all { it.labels.isEmpty() }


    fun reset() {
        resetCoroutine.launch {
            searchFilterRepository.resetFilter()
        }
    }

    fun updateBasic(id: Int, minValue: Float?, maxValue: Float?) {
        updateCoroutine.launch {
            searchFilterRepository.updateBasic(id, minValue, maxValue)
        }
    }
}