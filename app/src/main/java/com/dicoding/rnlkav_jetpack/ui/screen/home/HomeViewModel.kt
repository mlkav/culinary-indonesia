package com.dicoding.rnlkav_jetpack.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rnlkav_jetpack.data.CulinaryRepository
import com.dicoding.rnlkav_jetpack.model.Culinary
import com.dicoding.rnlkav_jetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repository: CulinaryRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    val uiState: StateFlow<UiState<List<Culinary>>> = _query
        .combine(repository.getAllCulinary()) { query, culinaryList ->
            val filteredList = culinaryList.filter {
                it.name.contains(query, ignoreCase = true)
            }
            UiState.Success(filteredList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun search(newQuery: String) {
        _query.value = newQuery
    }
}
