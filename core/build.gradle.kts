import com.vadmax.AppBuildInfo

val kotlinVersion: String by rootProject.extra
val coroutinesVersion: String by rootProject.extra

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val appBuildInfo: AppBuildInfo by rootProject.extra

android {
    namespace = "com.vadmax.timetosleep.core"
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
}
dependencies {
    api(libs.androidx.core)
    api(libs.kotlinx.collections.immutable)

    api(platform(libs.kotlin.bom))

    api(libs.androidx.lifecycle.livedata)

    // DI
    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.koin.android.ext)
    api(libs.koin.annotation)

    // Logger
    api(libs.timber)

    // Gson
    api(libs.google.gson)

    api(libs.androidx.work)

    // Tests
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
