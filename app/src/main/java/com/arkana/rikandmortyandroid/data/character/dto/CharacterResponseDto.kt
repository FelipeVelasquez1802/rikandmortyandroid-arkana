package com.arkana.rikandmortyandroid.data.character.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterResponseDto(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
) {
    fun getStatusEnum() = StatusEnum.fromString(status)
}
