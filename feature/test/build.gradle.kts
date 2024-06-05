
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
}

android {
    namespace = "com.juzabel.test"
    setupCommonAndroid()
    kotlinOptions {
        setKotlinJvmVersion()
    }
}

dependencies {
    implementation(project(path = ":common:local"))
    implementation(project(path = ":common:network"))
    implementation(libs.bundles.database.bundle)
    implementation(libs.bundles.koin.bundle)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
}
