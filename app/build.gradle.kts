@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ktlint.get().pluginId)
    id(libs.plugins.detekt.get().pluginId)
}

android {
    namespace = "com.juzabel.template"
    setupDefaultCompileSdk()
    defaultConfig {
        applicationId = "com.juzabel.template"
        versionCode = 1
        versionName = "1.0"
        setupDefaultConfig()
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    setCompileOptions()
    kotlinOptions {
        setKotlinJvmVersion()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    ktlint {
        version.set(libs.versions.ktlint.version)
        android.set(true)
        enableExperimentalRules.set(true)
    }
}

dependencies {
    implementation(project(":feature:userlist"))
    implementation(project(path = ":common:local"))
    implementation(project(path = ":common:network"))
    implementation(project(path = ":common:util"))
    implementation(project(":common:data"))

    implementation(libs.bundles.koin.view.bundle)
    implementation(libs.bundles.ktx.bundle)
    implementation(libs.bundles.compose.bundle)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.koin.bundle)
    implementation(libs.navigation.compose)
    implementation(project(":feature:userdetail"))
    testImplementation(libs.bundles.koin.test.bundle)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.android.test.bundle)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.bundles.debug.bundle)
}
