package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.network.DiceRollAPI
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DiceRollAPITest {
    @Test
    fun `Successful Request`() = runTest {
        val engine = MockEngine {
            assertEquals("https://rolz.org/api/?=10d12.json", it.url.toString())
            respond(
                content = """{"input":"10d12","result":44,"details":" (11 +2 +4 +2 +10 +2 +8 +3 +1 +1) ","code":"","illustration":"<span style=\"color: gray;\"><\/span> <span class=\"dc_dice_a\">10<\/span><span class=\"dc_dice_d\">D12<\/span>","timestamp":1692573592,"x":1692573592}""",
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val api = DiceRollAPI(engine)
        val result = api.rollDice(10, 12)
        assertTrue { result.input == "10d12" }
    }

    @Test
    fun `Failed Request`() = runTest {
        val engine = MockEngine {
            respond(
                content = "",
                status = HttpStatusCode.NotFound
            )
        }
        val api = DiceRollAPI(engine)
        assertFailsWith<ClientRequestException> {
            api.rollDice(10, 4)
        }
    }
}