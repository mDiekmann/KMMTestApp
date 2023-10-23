package com.mtd.kmmtestapp.models

import com.mtd.kmmtestapp.db.RollEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

class RollInfoModel (
    val rollTime: Instant,
    val total: Int,
    val equation: String,
    val resultsArr: Array<Int>
) {
    companion object {
        fun fromEntity(rollEntity: RollEntity): RollInfoModel {
            // because the returned value from the server includes nano seconds need to convert to instant first
            val rollDate = rollEntity.creationDate.toInstant()
            val resultsArr = rollEntity.diceValues.map {
                it.value
            }.toTypedArray()

            return RollInfoModel(
                rollDate,
                rollEntity.totalValue.toInt(),
                rollEntity.rollEquation,
                resultsArr
            )
        }
    }
}