package com.arkana.rikandmortyandroid.di

import com.arkana.rikandmortyandroid.data.common.network.ApiClient
import com.arkana.rikandmortyandroid.data.common.network.KtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule =
    module {
        single<HttpClient> {
            KtorClient.instance
        }

        single {
            ApiClient(baseUrl = "https://rickandmortyapi.com/api")
        }
    }
