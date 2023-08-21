package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.models.DiceRoll
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class DiceRollAPIImpl(private val engine: HttpClientEngine) : KoinComponent, DiceRollAPI {

    private val client = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }

        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }
    override suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll {
        return client.get{
            url {
                protocol = URLProtocol.HTTPS
                host = "rolz.org"
                path("api/")
                parameters.run { append("", "${diceCount}d$diceSides.json") }
            }
        }.body()
    }
}