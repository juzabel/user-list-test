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
        maven(uri("https://plugins.gradle.org/m2/"))
    }
}

rootProject.name = "User List"
include(":app")
include(":common")
include(":common:network")
include(":common:local")
include(":common:util")
include(":feature")
include(":feature:userlist")
include(":common:domainerrors")
include(":common:errors")
include(":common:resources")
include(":common:resources")
