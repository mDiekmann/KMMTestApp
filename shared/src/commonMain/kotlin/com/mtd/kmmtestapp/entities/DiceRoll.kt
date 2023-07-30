package com.mtd.kmmtestapp.entities

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Serializable
data class DiceRoll(
    @SerialName("input")
    val input: String,
    @SerialName("result")
    val result: Int,
    @SerialName("details")
    val details: String,
    @SerialName("timestamp")
    val rollTimeUTC: String
) {
    var rollTime = rollTimeUTC.toInstant().toLocalDateTime(TimeZone.UTC)
}

/*
{
    "input":"10d12",
    "result":61,
    "details":" (5 +9 +12 +4 +7 +2 +4 +9 +7 +2) ",
    "code":"",
    "illustration":"<span style=\"color: gray;\"><\/span> <span class=\"dc_dice_a\">10<\/span><span class=\"dc_dice_d\">D12<\/span>",
    "timestamp":1690413933,
    "x":1690413933
   }
 */