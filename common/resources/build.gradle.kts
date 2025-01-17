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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.bundles.compose.bundle)
    implementation(platform(libs.compose.bom))
}
