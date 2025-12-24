package com.arkana.rikandmortyandroid.data.character.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterListResponseDto(
    val info: InfoDto,
    val results: List<CharacterResponseDto>,
)

@Serializable
internal data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null,
)
