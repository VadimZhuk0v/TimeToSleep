package com.vadmax.extentions

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.vadmax.VariableType
import com.vadmax.constants.AdConstants
import com.vadmax.constants.Config
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

fun Project.configureBuildTypes() =
    this.extensions.getByType<BaseAppModuleExtension>().run {
        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
    }

fun Project.configureSign() =
    this.extensions.getByType<BaseAppModuleExtension>().run {
        signingConfigs {
            create("release") {
                storeFile = file("../key")
                storePassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
                keyAlias = "prod"
                keyPassword = "wKdW9KKWCtEufUCKsqMaFdW4sc5PwG5"
            }
        }
    }

fun Project.configureFlavors() =
    this.extensions.getByType<BaseAppModuleExtension>().run {
        defaultConfig {
            buildConfig(Config.ENABLE_CRASHLYTICS, VariableType.Boolean(false))
            buildConfig(Config.ENABLE_ANALYTICS, VariableType.Boolean(false))
            buildConfig(Config.AD_HEADER_UNIT, VariableType.String(AdConstants.AD_TEST_BANNER_UNIT))
            buildConfig(
                Config.AD_INTERSTITIAL_UNIT,
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
                buildConfig(Config.ENABLE_CRASHLYTICS, VariableType.Boolean(true))
            }
            create("prod") {
                buildConfig(Config.ENABLE_CRASHLYTICS, VariableType.Boolean(true))
                buildConfig(Config.ENABLE_ANALYTICS, VariableType.Boolean(true))
                buildConfig(
                    Config.AD_HEADER_UNIT,
                    VariableType.String(AdConstants.AD_PROD_BANNER_UNIT),
                )
                buildConfig(
                    Config.AD_INTERSTITIAL_UNIT,
                    VariableType.String(AdConstants.AD_PROD_INTERSTITIAL_UNIT),
                )
            }
        }
    }
