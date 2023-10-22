package com.mtd.kmmtestapp.network.models

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

@Serializable
data class RollRequestModel(
    val dice: Array<DiceRequestModel>,
    val room: String?
)

@Serializable
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
data class DiceRequestModel(
    val type: String,
    @EncodeDefault
    val theme: String = "dddice-red"
)