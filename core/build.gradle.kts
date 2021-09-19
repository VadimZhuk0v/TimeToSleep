import com.vadmax.constants.Dependencies
import com.vadmax.extentions.configureAndroidSubmodule

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("koin")
}

android {
    configureAndroidSubmodule()
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Timber
    api("com.github.ajalt:timberkt:1.5.1")
    api("no.nordicsemi.android:log-timber:2.3.0")

    // Koin
    Dependencies.koin.forEach { api(it) }

    // Kotlin Coroutines
    Dependencies.coroutines.forEach { api(it) }

    api("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")

    testImplementation("androidx.test:core-ktx:1.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
}
