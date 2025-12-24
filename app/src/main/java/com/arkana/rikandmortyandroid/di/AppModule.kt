package com.arkana.rikandmortyandroid.di

import com.arkana.rikandmortyandroid.data.character.repository.CharacterRepository
import com.arkana.rikandmortyandroid.data.character.repository.CharacterRepositoryImpl
import com.arkana.rikandmortyandroid.ui.character.viewmodel.CharacterListViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        singleOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
        viewModelOf(::CharacterListViewModel)
    }
