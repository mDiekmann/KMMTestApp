package com.mtd.kmmtestapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform