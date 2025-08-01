plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinCocoapods)
//    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    alias(libs.plugins.rickclephas)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    jvmToolchain(21)

// Target declarations - add or remove as needed below. These define
// which platforms this KMP module supports.
// See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.example.shared"
        compileSdk = 35
        minSdk = 26

//        withHostTestBuilder {
//        }
//
//        withDeviceTestBuilder {
//            sourceSetTreeName = "test"
//        }.configure {
//            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        }
    }

// For iOS targets, this is also where you should
// configure native binary output. For more information, see:
// https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

// A step-by-step guide on how to include this library in an XCode
// project can be found here:
// https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "sharedKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

// Source set declarations.
// Declaring a target automatically creates a source set with the same name. By default, the
// Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
// common to share sources between related targets.
// See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                // Add KMP dependencies here
                api(libs.kotlinx.coroutines.core)
                implementation(libs.koin.core)
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.gitlive.firebase.auth)
                implementation(libs.gitlive.firebase.firestore)
                implementation(libs.androidx.room.runtime)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.bundles.koin.kmp)

                implementation(libs.androidx.lifecycle.viewmodel)
                api(libs.rickclephas.kmp)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain. }
                implementation(libs.koin.android)
                implementation(libs.koin.compose)
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.room.ktx)
                implementation(libs.bundles.room)
            }

//        getByName("androidDeviceTest") {
//            dependencies {
//                implementation(libs.androidx.runner)
//                implementation(libs.androidx.core)
//                implementation(libs.androidx.junit)
//            }
//        }

            iosMain {
                dependencies {
                    api(libs.kotlinx.coroutines.core)
                    // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                    // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                    // part of KMP’s default source set hierarchy. Note that this source set depends
                    // on common by default and will correctly pull the iOS artifacts of any
                    // KMP dependencies declared in commonMain.
                }
            }
        }

    }
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "18.5"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "CommonKmp"
            export(libs.kotlinx.coroutines.core)
            isStatic = true
            export(libs.rickclephas.kmp)
        }

    }
}
room {
    schemaDirectory("$projectDir/schemas")
}
dependencies {
    ksp(libs.androidx.room.compiler)
}