// ktlint-disable max-line-length
buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${com.vadmax.constants.GeneralVersion.gradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${com.vadmax.constants.GeneralVersion.kotlin}")
        classpath("io.insert-koin:koin-gradle-plugin:${com.vadmax.constants.DependencyVersion.koin}")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.12")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config = rootProject.files("detekt/config.yml")
        // point to your custom config defining rules to run, overwriting default behavior
        // baseline = file("$projectDir/detekt/baseline.xml")
        // a way of suppressing issues before introducing detekt

        reports {
            html {
                destination = file("build/reports/detekt.html")
            }

            xml.enabled = false
            txt.enabled = false
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
            freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
        }
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "1.8"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
