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
    implementation(project(":local"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
