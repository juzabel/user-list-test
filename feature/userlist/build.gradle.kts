@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.com.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
}

android {
    namespace = "com.juzabel.userlist"
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
    implementation(project(path = ":common:local"))
    implementation(project(path = ":common:network"))
    implementation(project(path = ":common:util"))
    implementation(project(path = ":common:errors"))
    implementation(project(path = ":common:viewmodel"))
    implementation(project(path = ":common:data"))
    implementation(project(path = ":common:resources"))
    implementation(libs.bundles.compose.bundle)
    implementation(libs.bundles.paging.bundle)
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.bundles.debug.bundle)
    implementation(libs.bundles.database.bundle)
    implementation(libs.bundles.koin.bundle)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
}
