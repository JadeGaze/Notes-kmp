import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

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
    alias(libs.plugins.kotlinCocoapods) apply false

    id("io.gitlab.arturbosch.detekt") version("1.23.8")
}

detekt {
    toolVersion = "1.23.8"
    config.setFrom(files("$projectDir/detekt.yml"))
    buildUponDefaultConfig = true
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
}

tasks.register("lintAll") {
    dependsOn("detekt")
//    dependsOn("swiftlint")    TODO: раскомментировать строку после добавления swiftlint'а
}
// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}