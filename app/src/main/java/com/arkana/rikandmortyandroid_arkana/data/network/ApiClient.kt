package com.arkana.rikandmortyandroid_arkana.data.network

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiClient(val baseUrl: String) {

    val client = KtorClient.instance
    suspend inline fun <reified T> get(
        endpoint: String,
        parameters: Map<String, Any>? = null
    ): T {
        return client.get(baseUrl + endpoint) {
            parameters?.forEach { (key, value) ->
                parameter(key, value)
            }
        }.body()
    }

    suspend inline fun <reified T> post(
        endpoint: String,
        body: Any
    ): T {
        return client.post(baseUrl + endpoint) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    /**
     * Realiza una petición PUT.
     *
     * @param endpoint El endpoint de la API (relativo a baseUrl)
     * @param body El cuerpo de la petición
     * @return La respuesta deserializada al tipo T
     */
    suspend inline fun <reified T> put(
        endpoint: String,
        body: Any
    ): T {
        return client.put(baseUrl + endpoint) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    /**
     * Realiza una petición DELETE.
     *
     * @param endpoint El endpoint de la API (relativo a baseUrl)
     * @return La respuesta deserializada al tipo T
     */
    suspend inline fun <reified T> delete(
        endpoint: String
    ): T {
        return client.delete(baseUrl + endpoint).body()
    }

    /**
     * Realiza una petición GET que retorna una lista.
     *
     * @param endpoint El endpoint de la API (relativo a baseUrl)
     * @param parameters Parámetros de query opcionales
     * @return Lista de elementos del tipo T
     */
    suspend inline fun <reified T> getList(
        endpoint: String,
        parameters: Map<String, Any>? = null
    ): List<T> {
        return client.get(baseUrl + endpoint) {
            parameters?.forEach { (key, value) ->
                parameter(key, value)
            }
        }.body()
    }
}