@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.plugin.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
}

android {
    namespace = "com.juzabel.local"
    setupCommonAndroid()
    kotlinOptions {
        setKotlinJvmVersion()
    }
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.bundles.database.bundle)
    implementation(libs.core.ktx)
    implementation(libs.bundles.koin.bundle)
}
