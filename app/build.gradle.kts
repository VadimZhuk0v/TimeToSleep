import com.vadmax.AppBuildInfo
import com.vadmax.constants.AdConstants
import com.vadmax.constants.Config
import com.vadmax.constants.Config.ENABLE_CRASHLYTICS
import java.util.regex.Pattern.compile

val composeVersion: String by rootProject.extra

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val appBuildInfo: AppBuildInfo by rootProject.extra

android {
    namespace = "com.vadmax.timetosleep"
    compileSdk = appBuildInfo.compileSdk

    defaultConfig {
        applicationId = "com.vadmax.timetosleep"
        minSdk = appBuildInfo.minSdk
        targetSdk = appBuildInfo.targetSdk
        versionCode = 1
        versionName = "2.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("Boolean", "ENABLE_CRASHLYTICS", "true")
        buildConfigField("Boolean", Config.ENABLE_ANALYTICS, false.toString())
        buildConfigField(
            "String",
            Config.AD_HEADER_UNIT,
            "\"${AdConstants.AD_TEST_BANNER_UNIT}\"",
        )
        buildConfigField(
            "String",
            Config.AD_INTERSTITIAL_UNIT,
            "\"${AdConstants.AD_TEST_INTERSTITIAL_UNIT}\"",
        )
    }

    signingConfigs {
        create("prod") {
            storeFile = file("../key")
            storePassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
            keyAlias = "prod"
            keyPassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
        }
    }

    flavorDimensions += "dimens"
    productFlavors {
        val releaseSigningConfigs = signingConfigs.getByName("prod")
        create("dev") {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "Time To Sleep DEV")
            buildConfigField("Boolean", ENABLE_CRASHLYTICS, "false")
        }
        create("prod") {
            resValue("string", "app_name", "Time To Sleep")
            signingConfig = releaseSigningConfigs
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint.disable += "UnusedMaterial3ScaffoldPaddingParameter"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":domain"))

    implementation("com.google.android.material:material:1.12.0")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:6.4.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    val flavor = getCurrentFlavor()
    kotlinOptions.freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    kotlinOptions.freeCompilerArgs += "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
    kotlinOptions.freeCompilerArgs +=
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    kotlinOptions.freeCompilerArgs += "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
    kotlinOptions.freeCompilerArgs += "-opt-in=androidx.compose.ui.text.ExperimentalTextApi"
    if (flavor == "dev") {
        kotlinOptions.freeCompilerArgs +=
            listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_metrics",
            )
        kotlinOptions.freeCompilerArgs +=
            listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_metrics",
            )
    }
}

fun getCurrentFlavor(): String {
    val tskReqStr = gradle.startParameter.taskRequests.toString()

    val pattern =
        if (tskReqStr.contains("assemble")) {
            compile("assemble(\\w+)(Release|Debug)")
        } else if (tskReqStr.contains("bundle")) {
            compile("bundle(\\w+)(Release|Debug)")
        } else {
            compile("generate(\\w+)(Release|Debug)")
        }

    val matcher = pattern.matcher(tskReqStr)

    return if (matcher.find()) {
        matcher.group(1).lowercase()
    } else {
        ""
    }
}
