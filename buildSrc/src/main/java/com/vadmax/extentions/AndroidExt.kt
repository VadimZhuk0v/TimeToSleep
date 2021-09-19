package com.vadmax.extentions

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.vadmax.constants.Application
import com.vadmax.constants.DependencyVersion
import com.vadmax.constants.GeneralVersion
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidSubmodule() = this.extensions.getByType<LibraryExtension>().run {
    compileSdk = GeneralVersion.compileSdk

    defaultConfig {
        minSdk = GeneralVersion.minSdk
        targetSdk = GeneralVersion.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

fun Project.configureAndroidApplication() =
    this.extensions.getByType<BaseAppModuleExtension>().run {
        compileSdk = GeneralVersion.compileSdk

        defaultConfig {
            minSdk = GeneralVersion.minSdk
            targetSdk = GeneralVersion.targetSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }
        compileOptions {
            sourceCompatibility(JavaVersion.VERSION_1_8)
            targetCompatibility(JavaVersion.VERSION_1_8)
        }
        packagingOptions {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        defaultConfig {
            applicationId = Application.id
            versionCode = Application.versionCode
            versionName = Application.version
        }

        configureBuildTypes()

        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = DependencyVersion.compose
        }
    }

private fun Project.configureBuildTypes() =
    this.extensions.getByType<BaseAppModuleExtension>().run {
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
