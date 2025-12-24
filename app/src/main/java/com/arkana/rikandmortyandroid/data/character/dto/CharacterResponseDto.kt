package com.arkana.rikandmortyandroid.data.character.dto

internal data class CharacterResponseDto(
    val image: String,
    val name: String,
    private val status: String,
) {
    fun getStatusEnum() = StatusEnum.fromString(status)
}
