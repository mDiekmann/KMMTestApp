package com.mtd.kmmtestapp.data.models

import com.mtd.kmmtestapp.models.DiceSides
import kotlinx.serialization.Serializable

@Serializable
data class DiceEntity (
    val type: DiceSides,
    val value: Int
)