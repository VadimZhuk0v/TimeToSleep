import com.vadmax.constants.DependencyVersion
import com.vadmax.extentions.configureAndroidApplication

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/vadimzhukov/Work/AndroidStudioProjects/Timetosleep/key")
            storePassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
            keyAlias = "prod"
            keyPassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    configureAndroidApplication()
}

dependencies {
    implementation(project(":io"))
    implementation(project(":core"))
    com.vadmax.constants.Dependencies.compose.forEach {
        implementation(it)
    }

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.work:work-runtime-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:28.4.0"))

    // Lottie
    implementation("com.airbnb.android:lottie-compose:4.1.0")

    // AdMob
    implementation("com.google.android.gms:play-services-ads:20.3.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${DependencyVersion.compose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${DependencyVersion.compose}")
}
