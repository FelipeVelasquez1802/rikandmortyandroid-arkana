package com.arkana.rikandmortyandroid.di

import com.arkana.rikandmortyandroid.ui.character.viewmodel.CharacterListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf(::CharacterListViewModel)
    }
