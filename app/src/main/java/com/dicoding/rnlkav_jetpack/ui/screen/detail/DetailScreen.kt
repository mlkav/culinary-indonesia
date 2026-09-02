package com.dicoding.rnlkav_jetpack.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.rnlkav_jetpack.di.Injection
import com.dicoding.rnlkav_jetpack.ui.ViewModelFactory
import com.dicoding.rnlkav_jetpack.ui.common.UiState

@Composable
fun DetailScreen(
    culinaryId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(culinaryId) {
        viewModel.getCulinaryById(culinaryId)
    }

    when (val state = uiState) {
        is UiState.Loading -> {
            // Loading
        }
        is UiState.Success -> {
            val data = state.data
            DetailContent(
                name = data.name,
                photoUrl = data.photoUrl,
                origin = data.origin,
                price = data.price,
                description = data.description,
                isFavorite = data.isFavorite,
                onBackClick = navigateBack,
                onFavoriteClick = {
                    viewModel.updateFavoriteCulinary(data.id, !data.isFavorite)
                },
                modifier = Modifier.testTag("detail_content")
            )
        }
        is UiState.Error -> {
            // Error
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    photoUrl: String,
    origin: String,
    price: String,
    description: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        DetailHeader(
            photoUrl = photoUrl,
            onBackClick = onBackClick,
            modifier = Modifier.testTag("detail_header")
        )
        DetailInfo(
            name = name,
            origin = origin,
            price = price,
            description = description,
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick,
            modifier = Modifier
                .padding(16.dp)
                .testTag("detail_info")
        )
    }
}

@Composable
fun DetailHeader(
    photoUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .testTag("back_button")
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun DetailInfo(
    name: String,
    origin: String,
    price: String,
    description: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.testTag("favorite_button")
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Hapus dari favorit" else "Tambah ke favorit",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
        Text(
            text = origin,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = price,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
