package com.dicoding.rnlkav_jetpack.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rnlkav_jetpack.data.CulinaryRepository
import com.dicoding.rnlkav_jetpack.model.Culinary
import com.dicoding.rnlkav_jetpack.ui.common.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(private val repository: CulinaryRepository) : ViewModel() {

    val uiState: StateFlow<UiState<List<Culinary>>> = repository.getFavoriteCulinary()
        .map { culinary ->
            UiState.Success(culinary)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )
}
