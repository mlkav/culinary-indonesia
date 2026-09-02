package com.dicoding.rnlkav_jetpack.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.rnlkav_jetpack.di.Injection
import com.dicoding.rnlkav_jetpack.model.Culinary
import com.dicoding.rnlkav_jetpack.ui.ViewModelFactory
import com.dicoding.rnlkav_jetpack.ui.common.UiState
import com.dicoding.rnlkav_jetpack.ui.components.CulinaryItem
import com.dicoding.rnlkav_jetpack.ui.components.EmptyState
import com.dicoding.rnlkav_jetpack.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        query = query,
        onQueryChange = viewModel::search,
        uiState = uiState,
        navigateToDetail = navigateToDetail,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    uiState: UiState<List<Culinary>>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            modifier = Modifier.testTag("search_bar")
        )
        when (val state = uiState) {
            is UiState.Loading -> {
                // Loading UI if needed
            }
            is UiState.Success -> {
                CulinaryList(
                    culinaryList = state.data,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier.testTag("culinary_list")
                )
            }
            is UiState.Error -> {
                // Error UI
            }
        }
    }
}

@Composable
fun CulinaryList(
    culinaryList: List<Culinary>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (culinaryList.isEmpty()) {
        EmptyState(modifier = Modifier.testTag("empty_state"))
    } else {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = modifier
        ) {
            items(culinaryList, key = { it.id }) { culinary ->
                CulinaryItem(
                    name = culinary.name,
                    photoUrl = culinary.photoUrl,
                    origin = culinary.origin,
                    price = culinary.price,
                    modifier = Modifier
                        .clickable { navigateToDetail(culinary.id) }
                        .testTag("culinary_item_${culinary.id}")
                )
            }
        }
    }
}
