buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.perf.plugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.room) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}