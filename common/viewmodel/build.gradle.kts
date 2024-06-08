@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
}

android {
    namespace = "com.juzabel.common.viewmodel"
    setupCommonAndroid()
    kotlinOptions {
        setKotlinJvmVersion()
    }
}

dependencies {
    implementation(project(path = ":common:util"))
    implementation(project(path = ":common:errors"))
    implementation(libs.core.ktx)
    implementation(libs.bundles.ktx.bundle)
}