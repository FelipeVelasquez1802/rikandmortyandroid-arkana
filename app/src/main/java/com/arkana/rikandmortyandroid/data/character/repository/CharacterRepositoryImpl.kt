package com.arkana.rikandmortyandroid.data.character.repository

import com.arkana.rikandmortyandroid.data.character.dto.CharacterListResponseDto
import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto
import com.arkana.rikandmortyandroid.data.common.network.ApiClient

internal class CharacterRepositoryImpl(
    private val apiClient: ApiClient,
) : CharacterRepository {
    override suspend fun getCharacters(page: Int): Result<CharactersPage> =
        runCatching {
            val response =
                apiClient.get<CharacterListResponseDto>(
                    endpoint = "/character",
                    parameters = mapOf("page" to page),
                )
            CharactersPage(
                characters = response.results,
                currentPage = page,
                totalPages = response.info.pages,
            )
        }

    override suspend fun getCharacter(id: Int): Result<CharacterResponseDto> =
        runCatching {
            apiClient.get<CharacterResponseDto>(
                endpoint = "/character/$id",
            )
        }

    override suspend fun searchCharacters(
        name: String?,
        status: String?,
        page: Int,
    ): Result<CharactersPage> =
        runCatching {
            val parameters =
                buildMap {
                    put("page", page)
                    name?.let { put("name", it) }
                    status?.let { put("status", it) }
                }

            val response =
                apiClient.get<CharacterListResponseDto>(
                    endpoint = "/character",
                    parameters = parameters,
                )
            CharactersPage(
                characters = response.results,
                currentPage = page,
                totalPages = response.info.pages,
            )
        }
}
