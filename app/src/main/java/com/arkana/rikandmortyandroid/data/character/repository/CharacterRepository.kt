package com.arkana.rikandmortyandroid.data.character.repository

import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto

internal data class CharactersPage(
    val characters: List<CharacterResponseDto>,
    val currentPage: Int,
    val totalPages: Int,
)

internal interface CharacterRepository {
    suspend fun getCharacters(page: Int = 1): Result<CharactersPage>

    suspend fun getCharacter(id: Int): Result<CharacterResponseDto>

    suspend fun searchCharacters(
        name: String? = null,
        status: String? = null,
        page: Int = 1,
    ): Result<CharactersPage>
}
