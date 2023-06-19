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

rootProject.name = "FootballPro"
include(":app")
include(":core")
include(":compose-ui")
include(":core-database")
include(":core-network")
include(":feature")
include(":feature:matches")
include(":feature:transfers")
include(":feature:news")
include(":feature:leagues")
include(":feature:more")