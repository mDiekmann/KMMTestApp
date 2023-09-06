package com.mtd.kmmtestapp.models

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = DiceRollSerializer::class)
data class DiceRoll(
    val input: String,
    val result: Int,
    val details: String,
    @SerialName("timestamp")
    val rollTimeEpoch: Long
) {
    val rollTime = Instant.fromEpochMilliseconds(rollTimeEpoch).toLocalDateTime(TimeZone.UTC)
    val detailsAsIntArray = details
        .filter { it.isDigit() || it.isWhitespace() }
        .trim()
        .split("\\s".toRegex())
        .map {
            it.toInt()
        }
}

/*
{
    "input":"=10d12",
    "result":61,
    "details":" (5 +9 +12 +4 +7 +2 +4 +9 +7 +2) ",
    "code":"",
    "illustration":"<span style=\"color: gray;\"><\/span> <span class=\"dc_dice_a\">10<\/span><span class=\"dc_dice_d\">D12<\/span>",
    "timestamp":1690413933,
    "x":1690413933
   }
 */
