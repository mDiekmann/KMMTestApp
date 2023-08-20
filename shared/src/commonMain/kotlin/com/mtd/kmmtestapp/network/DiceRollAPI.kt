package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.entities.DiceRoll
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path

class DiceRollAPI(private val client: HttpClient) {

    suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll {
        return client.get{
            url {
                protocol = URLProtocol.HTTPS
                host = "rolz.org"
                path("api")
                parameters.run { append("", "${diceCount}d$diceSides") }
            }
        }.body()
    }
}