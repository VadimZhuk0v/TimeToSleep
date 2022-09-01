import com.vadmax.extentions.configureBuildTypes
import com.vadmax.extentions.configureFlavors
import com.vadmax.extentions.configureSign

val composeVersion: String by rootProject.extra

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = com.vadmax.constants.BuildVersion.compileSdk

    defaultConfig {
        minSdk = com.vadmax.constants.BuildVersion.minSdk
        targetSdk = com.vadmax.constants.BuildVersion.targetSdk
        applicationId = com.vadmax.constants.Application.id
        versionCode = com.vadmax.constants.Application.versionCode
        versionName = com.vadmax.constants.Application.version
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    configureSign()
    configureBuildTypes()
    configureFlavors()

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":domain"))

    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:4.1.0")

    // AdMob
    implementation("com.google.android.gms:play-services-ads:21.1.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}
