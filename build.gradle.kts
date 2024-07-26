import com.vadmax.AppBuildInfo

val appBuildInfo: AppBuildInfo by extra(
    AppBuildInfo(
        27,
        34,
        34,
    ),
)

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.1")
    }
}

plugins {
    val kotlinVersion = "1.9.23"
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
