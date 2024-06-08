@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
    id(libs.plugins.ksp.plugin.get().pluginId)
}

android {
    namespace = "com.juzabel.common.data"
    setupCommonAndroid()
    kotlinOptions {
        setKotlinJvmVersion()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(path = ":common:util"))
    implementation(project(path = ":common:viewmodel"))
    implementation(project(path = ":common:network"))
    implementation(project(path = ":common:errors"))
    ksp(libs.moshi.codegen)
    implementation(libs.moshi)
    implementation(libs.bundles.koin.bundle)
    implementation(libs.bundles.networking.bundle)
    implementation(libs.core.ktx)
    implementation(libs.datastore)
}
