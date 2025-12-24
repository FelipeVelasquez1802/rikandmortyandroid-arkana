package com.arkana.rikandmortyandroid.ui.character.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto

@Composable
internal fun CharacterItemScreen(character: CharacterResponseDto) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = character.name,
        )
    }
}

@Preview
@Composable
internal fun CharacterItemScreenPreview() {
    CharacterItemScreen(
        character =
            CharacterResponseDto(
                image = "",
                name = "Rick Sanchez",
                status = "Alive",
            ),
    )
}
