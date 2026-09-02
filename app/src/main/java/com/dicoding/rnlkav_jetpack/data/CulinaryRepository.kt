package com.dicoding.rnlkav_jetpack.data

import com.dicoding.rnlkav_jetpack.model.Culinary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class CulinaryRepository {

    private val _culinaryList = MutableStateFlow<List<Culinary>>(emptyList())
    val culinaryList: StateFlow<List<Culinary>> get() = _culinaryList

    init {
        if (_culinaryList.value.isEmpty()) {
            _culinaryList.value = CulinaryDataSource.dummyCulinary
        }
    }

    fun getAllCulinary(): Flow<List<Culinary>> {
        return culinaryList
    }

    fun getCulinaryById(culinaryId: Long): Flow<Culinary> {
        return culinaryList.map { list ->
            list.first { it.id == culinaryId }
        }
    }

    fun searchCulinary(query: String): Flow<List<Culinary>> {
        return culinaryList.map { list ->
            list.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun getFavoriteCulinary(): Flow<List<Culinary>> {
        return culinaryList.map { list ->
            list.filter { it.isFavorite }
        }
    }

    fun updateFavoriteCulinary(culinaryId: Long, newState: Boolean): Flow<Boolean> {
        val currentList = _culinaryList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == culinaryId }
        val result = if (index >= 0) {
            val culinary = currentList[index]
            currentList[index] = culinary.copy(isFavorite = newState)
            _culinaryList.value = currentList
            true
        } else {
            false
        }
        return kotlinx.coroutines.flow.flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: CulinaryRepository? = null

        fun getInstance(): CulinaryRepository =
            instance ?: synchronized(this) {
                CulinaryRepository().apply {
                    instance = this
                }
            }

        fun resetInstance() {
            instance = null
        }
    }
}
