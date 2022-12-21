package com.mtd.toolkit

import android.graphics.Color
import dev.icerock.moko.resources.ColorResource

actual typealias PlatformColor = Color

actual fun ColorResource.Single.toPlatformColor(): PlatformColor {
    return Color.valueOf(Color.BLUE)
}