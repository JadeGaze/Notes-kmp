import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
//    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm("desktop")

    sourceSets {
        val commonMain by getting
        val androidMain by getting
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.bundles.koin)
            implementation(libs.bundles.firebase)
            implementation(project(":shared"))
            implementation(project(":core:designsystem"))
            implementation(libs.kermit)
        }

        androidMain.dependencies {

        }

        desktopMain.dependencies {
//            implementation(compose.desktop.common)
        }
    }
}

android {
    namespace = "com.example.feature.folders.impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}