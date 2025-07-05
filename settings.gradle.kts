pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_NON_TRANSITIVE_DEPENDENCIES) // This is the correct line for Gradle 8.1.1
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ScientificCalculator"
include(":app")