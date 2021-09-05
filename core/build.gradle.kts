plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("koin")
}

android {
    compileSdk = 30
    defaultConfig {
        minSdk = 24
        targetSdk = 30

        // Make sure to use the AndroidJUnitRunner, or a subclass of it.
        // This requires a dependency on androidx.test:runner, too!
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // Timber
    api("com.github.ajalt:timberkt:1.5.1")
    api("no.nordicsemi.android:log-timber:2.3.0")

    // Koin main features for Android
    api("io.insert-koin:koin-android-ext:3.0.2")
    api("io.insert-koin:koin-androidx-workmanager:3.1.2")
    api("io.insert-koin:koin-androidx-compose:3.1.2")

    // Kotlin Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")

    api("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    testImplementation("androidx.test:core-ktx:1.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
}
