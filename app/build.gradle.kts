import com.vadmax.AppBuildInfo
import com.vadmax.constants.AdConstants
import com.vadmax.constants.Config
import com.vadmax.constants.Config.ENABLE_CRASHLYTICS
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.regex.Pattern.compile

val composeVersion: String by rootProject.extra

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)
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
        create("pc") {
            applicationIdSuffix = ".pc"
            versionNameSuffix = "-pc"
            resValue("string", "app_name", "Time To Sleep PC")
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

    implementation(libs.google.material)
    implementation(libs.kotlinx.serialization)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Lottie
    implementation(libs.lottie.compose)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}

composeCompiler {
    if (getCurrentFlavor() == "dev") {
        reportsDestination = layout.buildDirectory.dir("compose_metrics")
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        freeCompilerArgs.add("-opt-in=androidx.compose.animation.ExperimentalAnimationApi")
        freeCompilerArgs.add("-opt-in=androidx.compose.foundation.ExperimentalFoundationApi")
        freeCompilerArgs.add("-opt-in=androidx.compose.ui.ExperimentalComposeUiApi")
        freeCompilerArgs.add("-opt-in=androidx.compose.ui.text.ExperimentalTextApi")
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
