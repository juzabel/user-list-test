// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
   id(libs.plugins.android.application.get().pluginId) apply false
   id(libs.plugins.kotlin.android.get().pluginId) apply false
   id(libs.plugins.ktlint.get().pluginId) version(libs.plugins.ktlint.get().version.displayName) apply false
   id(libs.plugins.detekt.get().pluginId) version(libs.plugins.detekt.get().version.displayName) apply false
   id(libs.plugins.com.android.library.get().pluginId) apply false
   id(libs.plugins.ksp.plugin.get().pluginId) version(libs.plugins.ksp.plugin.get().version.displayName) apply false
}

buildscript {
    dependencies {
        classpath(libs.ktlint.gradle)
        classpath(libs.detekt.gradle.plugin)
    }
}
