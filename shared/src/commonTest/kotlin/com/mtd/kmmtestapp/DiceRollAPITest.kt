package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.DiceRollAPI
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DiceRollAPITest {
    private val apiMockEngine = DiceRollAPIMock()
    private val apiMock = DiceRollAPI(apiMockEngine.get())

    @Test
    fun `Test Roll with Room Slug`() = runTest {
        val result = apiMock.rollDice(2, DiceSides.d20, roomSlug = null)
        assertTrue { result.equation == "2d20" }
    }

    @Test
    fun `Test Roll without Room Slug`()  = runTest {
        val result = apiMock.rollDice(2, DiceSides.d20, "NSrQNmc")
        assertTrue { result.equation == "2d20" }
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
            api.rollDice(2, DiceSides.d20, roomSlug = null)
        }
    }
}