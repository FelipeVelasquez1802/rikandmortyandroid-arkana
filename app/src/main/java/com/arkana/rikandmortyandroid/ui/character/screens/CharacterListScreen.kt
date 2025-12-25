package com.arkana.rikandmortyandroid.ui.character.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto
import com.arkana.rikandmortyandroid.ui.character.screens.components.CharacterItemScreen
import com.arkana.rikandmortyandroid.ui.character.state.CharacterListWrapper
import com.arkana.rikandmortyandroid.ui.character.viewmodel.CharacterListViewModel
import com.arkana.rikandmortyandroid.ui.common.screens.components.AppContainer
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CharacterListScreen() {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()
    CharacterListContent(
        state = state,
        onLoadMore = { viewModel.loadNextPage() },
    )
}

@Composable
private fun CharacterListContent(
    state: CharacterListWrapper.State,
    onLoadMore: () -> Unit,
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by
        remember {
            derivedStateOf {
                val lastVisibleItem =
                    listState.layoutInfo.visibleItemsInfo.lastOrNull()
                        ?: return@derivedStateOf false

                val totalItems = listState.layoutInfo.totalItemsCount
                lastVisibleItem.index >= totalItems - 5
            }
        }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && state.hasMorePages && !state.loadingMore && !state.loading) {
            onLoadMore()
        }
    }

    AppContainer(
        loading = state.loading,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            items(
                items = state.characters,
                key = { character -> character.id },
            ) { character ->
                CharacterItemScreen(character)
            }

            if (state.loadingMore) {
                item {
                    LoadingItem()
                }
            }
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CharacterListScreenPreview() {
    CharacterListContent(
        state =
            CharacterListWrapper.State(
                characters =
                    listOf(
                        CharacterResponseDto(
                            id = 1,
                            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                            name = "Rick Sanchez",
                            status = "Alive",
                        ),
                    ),
            ),
        onLoadMore = {},
    )
}
