import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

object Config {
   val compileSdk = 34
   val minSdk = 26
   val kotlinJvmTarget = "11"
}

fun LibraryExtension.setupCommonAndroid() {
    compileSdk = Config.compileSdk

    defaultConfig {
        setupDefaultConfig()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    setCompileOptions()
}

fun CommonExtension<*,*,*,*,*>.setCompileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

fun BaseAppModuleExtension.setupDefaultCompileSdk() {
    compileSdk = Config.compileSdk
}

fun DefaultConfig.setupDefaultConfig() {
    minSdk = Config.minSdk
    if(this is ApplicationDefaultConfig){
        targetSdk = Config.compileSdk
    }
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

fun Project.configPlugins() {
    apply(from = rootProject.file("gradle/global-plugins.gradle.kts"))
}

fun KotlinJvmOptions.setKotlinJvmVersion() {
    jvmTarget = Config.kotlinJvmTarget
}