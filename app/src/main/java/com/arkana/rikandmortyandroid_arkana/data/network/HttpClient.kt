package com.arkana.rikandmortyandroid_arkana.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Cliente HTTP configurado con Ktor para realizar peticiones de red.
 *
 * Configuración:
 * - Motor Android nativo
 * - Serialización JSON automática
 * - Logging para desarrollo
 * - Headers por defecto
 */
object KtorClient {

    private const val TAG = "KtorClient"
    private const val REQUEST_TIMEOUT = 30_000L

    val instance: HttpClient by lazy {
        HttpClient(Android) {
            // Configuración del motor Android
            engine {
                connectTimeout = REQUEST_TIMEOUT.toInt()
                socketTimeout = REQUEST_TIMEOUT.toInt()
            }

            // Configuración de serialización JSON
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // Configuración de logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(TAG, message)
                    }
                }
                level = LogLevel.ALL
            }

            // Headers por defecto
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    /**
     * Cierra el cliente HTTP y libera recursos.
     * Llamar cuando la aplicación se destruya.
     */
    fun close() {
        instance.close()
    }
}