import com.vadmax.AppBuildInfo
import com.vadmax.constants.Config.ENABLE_CRASHLYTICS

val kotlinVersion: String by rootProject.extra
val coroutinesVersion: String by rootProject.extra

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    api("androidx.core:core-ktx:1.13.1")
    api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")

    api(platform("org.jetbrains.kotlin:kotlin-bom:1.9.23"))
    api("org.jetbrains.kotlin:kotlin-stdlib")

    api("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    // DI
    api("io.insert-koin:koin-android:3.5.4")
    api("io.insert-koin:koin-android-ext:3.0.2")

    // Logger
    api("com.jakewharton.timber:timber:5.0.1")

    // Gson
    api("com.google.code.gson:gson:2.10.1")

    api("androidx.work:work-runtime-ktx:2.9.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
    androidTestImplementation(kotlin("test"))
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
}
