package com.arkana.rikandmortyandroid

import android.app.Application
import com.arkana.rikandmortyandroid.di.appModule
import com.arkana.rikandmortyandroid.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RickAndMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RickAndMortyApplication)
            modules(
                networkModule,
                appModule,
            )
        }
    }
}
