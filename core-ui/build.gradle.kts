import com.vadmax.AppBuildInfo
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

val composeVersion: String by rootProject.extra

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

val appBuildInfo: AppBuildInfo by rootProject.extra

android {
    namespace = "com.vadmax.timetoseleep.coreui"
    compileSdk = appBuildInfo.compileSdk

    defaultConfig {
        minSdk = appBuildInfo.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "dimens"
    productFlavors {
        create("dev") {
        }
        create("prod") {
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))

    api(libs.androidx.lifecycle.runtime)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.activity.compose)
    api(libs.androidx.core.splashscreen)

    // DI
    api(libs.koin.androidx.compose)

    // Compose
    api(libs.androidx.constraintlayout.compose)
    api(libs.accompanist.permissions)
    api(libs.androidx.navigation.compose)
    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.compose.animation.graphics)
    api(libs.compose.runtime.livedata)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.material3)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_19)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

