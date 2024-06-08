plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    maven(uri("https://plugins.gradle.org/m2/"))
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
}