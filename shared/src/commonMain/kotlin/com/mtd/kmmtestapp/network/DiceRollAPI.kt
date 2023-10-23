package com.mtd.kmmtestapp.network

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.models.DiceRequestModel
import com.mtd.kmmtestapp.network.models.RollRequestModel
import com.mtd.kmmtestapp.network.models.RollResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DiceRollAPI(private val engine: HttpClientEngine) : DiceRollAPIInterface {

    private val logger = Logger.withTag("DiceRollAPIImpl")
    // obviously bad, but convenience on a test project
    private val apiKey = "oXmol7fhypcsPZHgvnum94PCXN4RJ7sRi128ZhZp107f707d"

    private val client = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "dddice.com"

                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    append(HttpHeaders.Authorization, "Bearer " + apiKey)
                    append(HttpHeaders.UserAgent, "KMP Test Client")
                }
            }
        }

        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }

    override suspend fun rollDice(
        diceCount: Int,
        diceSides: DiceSides,
        roomSlug: String?
    ): RollResponseModel {
        logger.v { "rollDice($diceCount,$diceSides)" }
        val diceArr = getDiceArray(diceCount, diceSides)
        val bodyContent = RollRequestModel(
            diceArr,
            roomSlug
        )
        return client.post{
            url {
                path("api/1.0/roll")
                setBody(bodyContent)
            }
        }.body()
    }

    private fun getDiceArray(
        diceCount: Int,
        diceSides: DiceSides
    ): Array<DiceRequestModel> {
        var diceList: MutableList<DiceRequestModel> = ArrayList()

        for (i in 1..diceCount) {
            diceList.add(DiceRequestModel(type = diceSides.name))
        }

        return diceList.toTypedArray()
    }
}
