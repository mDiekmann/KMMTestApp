package com.mtd.kmmtestapp.network

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceSides
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

class DiceRollAPI(private val engine: HttpClientEngine) : DiceRollAPIInterface {

    private val logger = Logger.withTag("DiceRollAPIImpl")

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
    override suspend fun rollDice(diceCount: Int, diceSides: DiceSides): DiceRoll {
        logger.v { "rollDice($diceCount,$diceSides)" }
        return client.get{
            url {
                protocol = URLProtocol.HTTPS
                host = "rolz.org"
                path("api/")
                parameters.run { append("", "${diceCount}${diceSides}.json") }
            }
        }.body()
    }
}
