pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_NON_TRANSITIVE_DEPENDENCIES)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ScientificCalculator"
include(":app")