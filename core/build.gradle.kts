import com.vadmax.extentions.configureAndroidSubmodule

val kotlinVersion: String by rootProject.extra
val coroutinesVersion: String by rootProject.extra

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
}

dependencies {
    // Timber
    api("com.github.ajalt:timberkt:1.5.1")
    api("no.nordicsemi.android:log-timber:2.3.0")
    api("androidx.core:core-ktx:1.8.0")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    // Koin
    api("io.insert-koin:koin-android-ext:3.0.2")
    api("io.insert-koin:koin-androidx-workmanager:3.1.2")
    api("io.insert-koin:koin-androidx-compose:3.2.0")

    // Kotlin Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Gson
    api("com.google.code.gson:gson:2.8.9")

    api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    testImplementation("androidx.test:core-ktx:1.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}
