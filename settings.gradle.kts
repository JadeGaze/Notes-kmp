pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Notes"
include(":app")

/* Folders module */
include(":feature:folders:impl")

/* Core module */
include(":core:designsystem")
include(":core:navigation")
include(":feature:notes:impl")
include(":feature:note:impl")
//include(":feature:auth:api")
include(":feature:auth:impl")
include(":shared")
include(":desktopApp")
