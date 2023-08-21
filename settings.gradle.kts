@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Safe SMS"

include(":app")
include(":core")
include(":data")
include(":domain")
include(":feature:home")
include(":feature:settings")
include(":feature:contact")
include(":feature:splash")
include(":feature:chat")
