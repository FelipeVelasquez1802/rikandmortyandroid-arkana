package com.arkana.rikandmortyandroid.ui.character.state

import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto

internal sealed interface CharacterListWrapper {
    data class State(
        val loading: Boolean = false,
        val loadingMore: Boolean = false,
        val characters: List<CharacterResponseDto> = emptyList(),
        val error: String? = null,
        val currentPage: Int = 1,
        val totalPages: Int = 1,
    ) {
        val hasMorePages: Boolean
            get() = currentPage < totalPages
    }
}
