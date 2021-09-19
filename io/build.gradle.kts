import com.vadmax.extentions.configureAndroidSubmodule

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
    implementation(project(":core"))

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    // Protobuf
    implementation("com.google.protobuf:protobuf-javalite:3.14.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.7")
}
