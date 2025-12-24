package com.arkana.rikandmortyandroid

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.arkana.rikandmortyandroid.di.appModule
import com.arkana.rikandmortyandroid.di.imageModule
import com.arkana.rikandmortyandroid.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent.get

class RickAndMortyApplication :
    Application(),
    SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RickAndMortyApplication)
            modules(
                networkModule,
                imageModule,
                appModule,
            )
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = get(ImageLoader::class.java)
}
