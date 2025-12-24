package com.arkana.rikandmortyandroid.ui.character.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto
import com.arkana.rikandmortyandroid.ui.character.screens.components.CharacterItemScreen
import com.arkana.rikandmortyandroid.ui.character.viewmodel.CharacterListViewModel
import com.arkana.rikandmortyandroid.ui.common.screens.components.AppContainer
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CharacterListScreen() {
    val viewModel = koinViewModel<CharacterListViewModel>()
    val state by viewModel.state.collectAsState()
    CharacterListContent(characters = state.characters)
}

@Composable
private fun CharacterListContent(characters: List<CharacterResponseDto>) {
    AppContainer {
        characters.forEach { character ->
            CharacterItemScreen(character)
        }
    }
}

@Preview
@Composable
private fun CharacterListScreenPreview() {
    CharacterListContent(
        characters =
            listOf(
                CharacterResponseDto(
                    id = 1,
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    name = "Rick Sanchez",
                    status = "Alive",
                ),
            ),
    )
}
