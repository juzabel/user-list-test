@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
}

android {
    namespace = "com.juzabel.resources"
    setupCommonAndroid()
    kotlinOptions {
        setKotlinJvmVersion()
    }
}

dependencies {
    implementation(libs.core.ktx)
}
