package com.arkana.rikandmortyandroid.ui.character.state

import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto

internal sealed interface CharacterListWrapper {
    data class State(
        val loading: Boolean = false,
        val characters: List<CharacterResponseDto> = emptyList(),
        val error: String? = null,
    )
}
