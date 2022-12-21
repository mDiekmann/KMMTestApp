package com.mtd.toolkit

import dev.icerock.moko.resources.ColorResource

expect class PlatformColor

expect fun ColorResource.Single.toPlatformColor(): PlatformColor