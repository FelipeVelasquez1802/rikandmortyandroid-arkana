package com.arkana.rikandmortyandroid.ui.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkana.rikandmortyandroid.data.character.dto.CharacterResponseDto
import com.arkana.rikandmortyandroid.ui.character.state.CharacterListWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CharacterListViewModel : ViewModel() {
    private val _state: MutableStateFlow<CharacterListWrapper.State> =
        MutableStateFlow(CharacterListWrapper.State())
    val state =
        _state
            .onStart { onStart() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CharacterListWrapper.State(),
            )

    fun onStart() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    characters =
                        listOf(
                            CharacterResponseDto(
                                image = "",
                                name = "Rick Sanchez",
                                status = "Alive",
                            ),
                        ),
                )
            }
        }
    }
}
