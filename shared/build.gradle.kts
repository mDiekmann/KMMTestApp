plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export(libs.moko.resources.core)
            export(libs.moko.graphics.core) // contains toUIColor helper function
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.bundles.ktor.common)
                implementation(libs.sqlDelight.coroutinesExt)
                implementation(libs.coroutines.core)
                implementation(libs.kotlinx.dateTime)
                implementation(libs.touchlab.kermit)
                api(libs.moko.resources.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.shared.commonTest)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.sqlDelight.android)
                implementation(libs.androidx.lifecycle.viewmodel)
                api(libs.moko.resources.compose)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.sqlDelight.jvm)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.native)
            }
        }

        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    namespace = "com.mtd.kmmtestapp"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.mtd.kmmtestapp.db")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.mtd.kmmtestapp.res" // required
    multiplatformResourcesClassName = "SharedRes" // optional, default MR
}