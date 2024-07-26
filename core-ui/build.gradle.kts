import com.vadmax.AppBuildInfo

val composeVersion: String by rootProject.extra

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    kotlinOptions {
        jvmTarget = "19"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    implementation(project(":core"))

    api("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    api("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    api("androidx.activity:activity-compose:1.9.1")
    api("androidx.core:core-splashscreen:1.0.1")

    // DI
    api("io.insert-koin:koin-androidx-compose:3.5.4")

    // Compose
    api("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    api("com.google.accompanist:accompanist-permissions:0.34.0")
    api("androidx.navigation:navigation-compose:2.7.7")
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    api(composeBom)
    androidTestImplementation(composeBom)
    debugImplementation(composeBom)
    api("androidx.compose.ui:ui")
    api("androidx.compose.animation:animation-graphics")
    api("androidx.compose.runtime:runtime-livedata")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
