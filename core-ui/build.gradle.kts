import com.vadmax.extentions.configureAndroidSubmodule

val composeVersion: String by rootProject.extra

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    configureAndroidSubmodule()
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {
    implementation(project(":core"))

    // Compose
    api("androidx.compose.ui:ui:$composeVersion")
    api("androidx.compose.material:material:$composeVersion")
    api("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    api("androidx.activity:activity-compose:1.5.1")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    api("androidx.navigation:navigation-compose:2.5.1")
    api("com.google.accompanist:accompanist-systemuicontroller:0.18.0")

    api("androidx.appcompat:appcompat:1.3.1")
    api("com.google.android.material:material:1.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
