package com.arkana.rikandmortyandroid.ui.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkana.rikandmortyandroid.data.character.repository.CharacterRepository
import com.arkana.rikandmortyandroid.ui.character.state.CharacterListWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CharacterListViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {
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
        loadCharacters()
    }

    private fun loadCharacters(page: Int = 1) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }

            repository
                .getCharacters(page)
                .onSuccess { characters ->
                    println("********************************")
                    println(characters)
                    _state.update { state ->
                        state.copy(
                            characters = characters,
                            loading = false,
                        )
                    }
                }.onFailure { error ->
                    _state.update { state ->
                        state.copy(
                            error = error.message ?: "Unknown error",
                            loading = false,
                        )
                    }
                }
        }
    }

    fun retry() {
        loadCharacters()
    }
}
