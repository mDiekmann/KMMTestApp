plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application") version libs.versions.android.gradle.plugin.get() apply false
    id("com.android.library") version libs.versions.android.gradle.plugin.get() apply false
    id("dev.icerock.mobile.multiplatform-resources") version libs.versions.moko.resources.get() apply false
    kotlin("android") version libs.versions.kotlin.get() apply false
    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    kotlin("plugin.serialization") version libs.versions.kotlin.get() apply false
    id("app.cash.sqldelight") version libs.versions.sqlDelight.get() apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.kmp.nativecoroutines)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}