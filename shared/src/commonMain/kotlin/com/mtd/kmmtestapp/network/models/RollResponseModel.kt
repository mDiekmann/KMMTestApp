package com.mtd.kmmtestapp.network.models

import com.mtd.kmmtestapp.database.util.DiceRollSerializer
import com.mtd.kmmtestapp.models.DiceSides
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

@Serializable(with = RollResponseSerializer::class)
data class RollResponseModel(
    val uuid: String,
    val created_at: String,
    val total_value: Int,
    val equation: String,
    @SerialName("values")
    val diceResults: List<DiceResponseModel>
)

@Serializable
data class DiceResponseModel(
    val type: DiceSides,
    val value: Int
)

// overkill to just skip over the "data" key layer,
// but good to check out the custom serializer
object RollResponseSerializer : KSerializer<RollResponseModel> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Asset") {
            element("data", buildClassSerialDescriptor("data") {
                element("uuid", String.serializer().descriptor)
                element("created_at", String.serializer().descriptor)
                element("total_value", Int.serializer().descriptor)
                element("equation", String.serializer().descriptor)
                element("values", ListSerializer(DiceResponseModel.serializer()).descriptor)
            })
        }

    override fun serialize(encoder: Encoder, value: RollResponseModel) {
        require(encoder is JsonEncoder)
        encoder.encodeJsonElement(buildJsonObject {
            put("data", buildJsonObject {
                put("uuid", value.uuid)
                put("created_at", value.created_at)
                put("total_value", value.total_value)
                put("equation", value.equation)
                put("values", JsonArray(value.diceResults.map {
                        encoder.json.encodeToJsonElement(it)
                    }))
            })
        })
    }

    override fun deserialize(decoder: Decoder): RollResponseModel {
        require(decoder is JsonDecoder)
        val root = decoder.decodeJsonElement()
        val element = root.jsonObject["data"]!!
        val results = element
            .jsonObject["values"]!!
            .jsonArray
            .map {
                decoder.json.decodeFromJsonElement<DiceResponseModel>(it)
            }
        return RollResponseModel(
            uuid = element.jsonObject["uuid"]!!.jsonPrimitive.content,
            created_at = element.jsonObject["created_at"]!!.jsonPrimitive.content,
            total_value = element.jsonObject["total_value"]!!.jsonPrimitive.content.toInt(),
            equation = element.jsonObject["equation"]!!.jsonPrimitive.content,
            diceResults = results
        )
    }
}
