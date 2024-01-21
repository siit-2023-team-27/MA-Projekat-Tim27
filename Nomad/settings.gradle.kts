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
        jcenter()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("jitpack.io") }
        maven("https://jitpack.io")
    }
}

rootProject.name = "Nomad"
include(":app")
 