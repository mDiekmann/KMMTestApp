plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
    id("co.touchlab.skie")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android ()
    jvmToolchain(17)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "CommonKMM"
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
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.skie.configuration.annotations)
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

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.native)
                implementation(libs.touchlab.kermit.simple)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosSimulatorArm64Test by getting
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosTest by getting {
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
}

android {
    namespace = "com.mtd.kmmtestapp"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    // TODO: Remove workaround for https://issuetracker.google.com/issues/260059413
    // otherwise attempts to run javac with v8 and causes a mismatch
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation(libs.androidx.preference.ktx)
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



android {
    sourceSets {
        getByName("main").java.srcDirs("build/generated/moko/androidMain/src")
    }
}