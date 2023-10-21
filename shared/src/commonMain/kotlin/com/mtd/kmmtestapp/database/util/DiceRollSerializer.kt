package com.mtd.kmmtestapp.database.util

import com.mtd.kmmtestapp.database.models.DiceRoll
import kotlinx.datetime.Clock
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
object DiceRollSerializer : KSerializer<DiceRoll> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DiceRoll") {
        element<String>("input")
        element<Int>("result")
        element<String>("details")
        element<Long>("timestamp")
    }

    override fun serialize(encoder: Encoder, value: DiceRoll) {
        // no need to make any changes
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.input)
            encodeIntElement(descriptor, 1, value.result)
            encodeStringElement(descriptor, 2, value.details)
            encodeLongElement(descriptor, 3, value.rollTimeEpoch)
        }
    }

    override fun deserialize(decoder: Decoder): DiceRoll {
        return decoder.decodeStructure(descriptor) {
            var input = ""
            var result = 0
            var details = ""
            var rollTimeEpoch = Clock.System.now().epochSeconds
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> input = decodeStringElement(descriptor, 0)
                    1 -> result = decodeIntElement(descriptor, 1)
                    2 -> details = decodeStringElement(descriptor, 2)
                    3 -> rollTimeEpoch = decodeLongElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            //clean up input, has a leading = for some reason
            val trimmedInput = input.trim('=')
            DiceRoll(
                input = trimmedInput,
                result = result,
                details = details,
                rollTimeEpoch = rollTimeEpoch
            )
        }
    }
}
