package com.arkana.rikandmortyandroid.data.character.repository

import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto

internal interface CharacterRepository {
    suspend fun getCharacters(page: Int = 1): Result<List<CharacterResponseDto>>

    suspend fun getCharacter(id: Int): Result<CharacterResponseDto>

    suspend fun searchCharacters(
        name: String? = null,
        status: String? = null,
        page: Int = 1,
    ): Result<List<CharacterResponseDto>>
}
