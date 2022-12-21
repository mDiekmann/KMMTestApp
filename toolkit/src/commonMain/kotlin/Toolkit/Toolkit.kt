package com.mtd.toolkit

object Toolkit {
    fun getTextColor(): PlatformColor {
        return UI.colors.textColor.toPlatformColor()
    }
}
