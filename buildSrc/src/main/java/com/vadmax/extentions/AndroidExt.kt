package com.vadmax.extentions

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.vadmax.VariableType
import com.vadmax.constants.AdConstants
import com.vadmax.constants.Application
import com.vadmax.constants.Config.AD_HEADER_UNIT
import com.vadmax.constants.Config.AD_INTERSTITIAL_UNIT
import com.vadmax.constants.Config.ENABLE_ANALYTICS
import com.vadmax.constants.Config.ENABLE_CRASHLYTICS
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

        signingConfigs {
            create("release") {
                storeFile = file("/Users/vadimzhukov/Work/AndroidStudioProjects/Timetosleep/key")
                storePassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
                keyAlias = "prod"
                keyPassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
            }
        }
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
        defaultConfig {
            buildConfig(ENABLE_CRASHLYTICS, VariableType.Boolean(false))
            buildConfig(ENABLE_ANALYTICS, VariableType.Boolean(false))
            buildConfig(AD_HEADER_UNIT, VariableType.String(AdConstants.AD_TEST_BANNER_UNIT))
            buildConfig(
                AD_INTERSTITIAL_UNIT,
                VariableType.String(AdConstants.AD_TEST_INTERSTITIAL_UNIT),
            )
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
                buildConfig(ENABLE_CRASHLYTICS, VariableType.Boolean(true))
            }
            create("prod") {
                buildConfig(ENABLE_CRASHLYTICS, VariableType.Boolean(true))
                buildConfig(ENABLE_ANALYTICS, VariableType.Boolean(true))
                buildConfig(AD_HEADER_UNIT, VariableType.String(AdConstants.AD_PROD_BANNER_UNIT))
                buildConfig(
                    AD_INTERSTITIAL_UNIT,
                    VariableType.String(AdConstants.AD_PROD_INTERSTITIAL_UNIT),
                )
            }
        }
    }
