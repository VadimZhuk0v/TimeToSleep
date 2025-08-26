import com.vadmax.AppBuildInfo
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinx.rcp)
}

val appBuildInfo: AppBuildInfo by rootProject.extra

android {
    namespace = "com.vadimax.timetosleep.remote"
    compileSdk = appBuildInfo.compileSdk

    defaultConfig {
        minSdk = appBuildInfo.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "dimens"
    productFlavors {
        create("dev") {
        }
        create("prod") {
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets["main"].java.srcDir("build/generated/ksp/main/kotlin")
}

dependencies {
    implementation(project(":core"))

    // DI
    ksp(libs.koin.ksp.compiler)

    // RCP
    implementation(libs.ktor.client.http)
    implementation(libs.logback.android)
    implementation(libs.kotlinx.rpc.client)
    implementation(libs.kotlinx.rpc.client.ktor)
    implementation(libs.kotlinx.rpc.json)
    implementation(libs.slf4j.api)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_19)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

