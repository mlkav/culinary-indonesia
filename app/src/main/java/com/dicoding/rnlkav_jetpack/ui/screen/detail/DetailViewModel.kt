package com.dicoding.rnlkav_jetpack.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.rnlkav_jetpack.data.CulinaryRepository
import com.dicoding.rnlkav_jetpack.model.Culinary
import com.dicoding.rnlkav_jetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CulinaryRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Culinary>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Culinary>>
        get() = _uiState

    fun getCulinaryById(culinaryId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getCulinaryById(culinaryId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { culinary ->
                    _uiState.value = UiState.Success(culinary)
                }
        }
    }

    fun updateFavoriteCulinary(culinaryId: Long, newState: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteCulinary(culinaryId, newState).collect {}
        }
    }
}
