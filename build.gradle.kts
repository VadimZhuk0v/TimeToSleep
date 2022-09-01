// ktlint-disable max-line-length
buildscript {
    val kotlinVersion by extra("1.7.10")
    val coroutinesVersion by extra("1.6.3")
    val composeVersion by extra("1.1.1")

    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.17")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        autoCorrect = true
        baseline = file("$rootDir/detekt/baseline.xml")
        config = files("$rootDir/detekt/config.xml")

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
    this.jvmTarget = "1.8"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
