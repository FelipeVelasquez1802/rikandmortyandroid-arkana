package com.arkana.rikandmortyandroid.di

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.ktor.client.HttpClient
import org.koin.dsl.module

val imageModule =
    module {
        single {
            ImageLoader
                .Builder(get<Context>())
                .components {
                    add(KtorNetworkFetcherFactory(httpClient = { get<HttpClient>() }))
                }
                .memoryCache {
                    MemoryCache
                        .Builder()
                        .maxSizePercent(get<Context>(), 0.25)
                        .build()
                }
                .diskCache {
                    DiskCache
                        .Builder()
                        .directory(get<Context>().cacheDir.resolve("image_cache"))
                        .maxSizeBytes(50 * 1024 * 1024)
                        .build()
                }
                .crossfade(true)
                .logger(DebugLogger())
                .build()
        }
    }
