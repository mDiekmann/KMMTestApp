package com.mtd.toolkit

import platform.UIKit.UIColor
import dev.icerock.moko.resources.ColorResource

actual typealias PlatformColor: UIColor

actual fun ColorResource.Single.toPlatormColor(): PlatformColor {
    return UIColor.blueColor
}

