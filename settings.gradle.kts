pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.of(RepositoriesMode.FailOnNonTransitiveDependencies)) // Corrected line
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ScientificCalculator"
include(":app")