package com.mtd.kmmtestapp.data.adapters

import app.cash.sqldelight.ColumnAdapter
import com.mtd.kmmtestapp.data.models.DiceEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DiceValuesAdapter(
    private val json: Json
): ColumnAdapter<List<DiceEntity>, String> {

    override fun decode(databaseValue: String): List<DiceEntity> =
        json.decodeFromString(databaseValue)

    override fun encode(value: List<DiceEntity>): String =
        json.encodeToString(value)
}