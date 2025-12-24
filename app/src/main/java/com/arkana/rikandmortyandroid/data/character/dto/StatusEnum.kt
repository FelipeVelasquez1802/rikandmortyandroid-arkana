package com.arkana.rikandmortyandroid.data.character.dto

internal enum class StatusEnum {
    ALIVE,
    DEAD,
    UNKNOW,
    ;

    companion object {
        fun fromString(value: String): StatusEnum =
            entries.find {
                it.name.equals(value, ignoreCase = true)
            } ?: UNKNOW
    }
}
