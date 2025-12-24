package com.arkana.rikandmortyandroid.data.character.dto

internal enum class StatusEnum(val value: String) {
    ALIVE(value = "Alive"),
    DEAD(value = "Dead"),
    UNKNOW(value = "Unknow"),
    ;

    companion object {
        fun fromString(value: String): StatusEnum =
            entries.find {
                it.name.equals(value, ignoreCase = true)
            } ?: UNKNOW
    }
}
