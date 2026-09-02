package com.dicoding.rnlkav_jetpack.ui.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.rnlkav_jetpack.di.Injection
import com.dicoding.rnlkav_jetpack.model.Culinary
import com.dicoding.rnlkav_jetpack.ui.ViewModelFactory
import com.dicoding.rnlkav_jetpack.ui.common.UiState
import com.dicoding.rnlkav_jetpack.ui.screen.home.CulinaryList

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoriteScreenContent(
        uiState = uiState,
        navigateToDetail = navigateToDetail,
        modifier = modifier
    )
}

@Composable
fun FavoriteScreenContent(
    uiState: UiState<List<Culinary>>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        when (val state = uiState) {
            is UiState.Loading -> {
                // Loading
            }
            is UiState.Success -> {
                CulinaryList(
                    culinaryList = state.data,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier.testTag("favorite_list")
                )
            }
            is UiState.Error -> {
                // Error
            }
        }
    }
}
