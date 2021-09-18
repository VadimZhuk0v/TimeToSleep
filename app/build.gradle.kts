val composeVersion = "1.0.2"

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
    compileSdk = 30

    defaultConfig {
        applicationId = "com.vadmax.timetosleep"
        minSdk = 23
        targetSdk = 30
        versionCode = 6
        versionName = "0.5"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions.add("dimension")
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
        create("qa") {
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
        }
        create("prod") {
        }
    }
}

dependencies {
    implementation(project(":io"))
    implementation(project(":core"))
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")
    implementation("androidx.work:work-runtime-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.18.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:28.4.0"))

    // Lottie
    implementation("com.airbnb.android:lottie-compose:4.1.0")

    // AdMob
    implementation("com.google.android.gms:play-services-ads:20.3.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}
