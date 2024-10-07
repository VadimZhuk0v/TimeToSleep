import com.vadmax.AppBuildInfo
import com.vadmax.constants.Config.ENABLE_CRASHLYTICS
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.FileInputStream
import java.util.Properties
import java.util.regex.Pattern.compile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.ksp)
}

val appBuildInfo: AppBuildInfo by rootProject.extra
val keystorePropertiesFile = rootProject.file("app/keystore.properties")
val keystoreProperties = Properties()

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
} else {
    println("keystore.properties file not found. Release builds may fail.")
}

android {
    namespace = "com.vadmax.timetosleep"
    compileSdk = appBuildInfo.compileSdk

    defaultConfig {
        applicationId = "com.vadmax.timetosleep.app"
        minSdk = appBuildInfo.minSdk
        targetSdk = appBuildInfo.targetSdk
        versionCode = 5
        versionName = "2.0.0-alpha02"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("Boolean", "ENABLE_CRASHLYTICS", true.toString())
    }

    signingConfigs {
        create("prod") {
            storeFile = keystoreProperties["storeFile"]?.let { file(it) }
            storePassword = keystoreProperties["storePassword"] as String?
            keyAlias = keystoreProperties["keyAlias"] as String?
            keyPassword = keystoreProperties["keyPassword"] as String?
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    composeCompiler {
        enableStrongSkippingMode = true
    }

    lint.disable += "UnusedMaterial3ScaffoldPaddingParameter"
    sourceSets["main"].java.srcDir("build/generated/ksp/main/kotlin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":domain"))

    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.kotlinx.serialization)
    implementation(libs.google.material)
    implementation(libs.kotlinx.serialization)

    // DI
    ksp(libs.koin.ksp.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Lottie
    implementation(libs.lottie.compose)

    // QR
    implementation(libs.qr.kit)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Custom Tabs
    implementation(libs.androidx.browser)

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
        freeCompilerArgs.add("-Xcontext-receivers")
        freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
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
