import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:navigation"))
                implementation(project(":feature:folders:impl"))
                implementation(project(":feature:notes:impl"))
                implementation(project(":feature:note:impl"))
//    implementation(project(":feature:auth:impl"))
                implementation(project(":feature:auth:impl"))

                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
                implementation(libs.bundles.koin)
                implementation(libs.bundles.firebase)
                implementation(libs.kotlinx.coroutines.core.jvm)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.datastore.preferences)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.example.notes.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
