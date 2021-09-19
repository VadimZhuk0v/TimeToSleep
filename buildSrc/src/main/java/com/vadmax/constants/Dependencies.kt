package com.vadmax.constants

object Dependencies {
    val compose = listOf(
        "androidx.compose.ui:ui:${DependencyVersion.compose}",
        "androidx.compose.material:material:${DependencyVersion.compose}",
        "androidx.compose.ui:ui-tooling-preview:${DependencyVersion.compose}",
        "androidx.compose.runtime:runtime-livedata:${DependencyVersion.compose}",
        "androidx.activity:activity-compose:1.3.1",
        "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07",
        "androidx.navigation:navigation-compose:2.4.0-alpha06",
        "com.google.accompanist:accompanist-systemuicontroller:0.18.0",
    )
    val koin = listOf(
        "io.insert-koin:koin-android-ext:3.0.2",
        "io.insert-koin:koin-androidx-workmanager:${DependencyVersion.koin}",
        "io.insert-koin:koin-androidx-compose:${DependencyVersion.koin}",
    )
    val coroutines = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersion.coroutines}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersion.coroutines}",
    )
    val firebase = listOf(
        "com.google.firebase:firebase-crashlytics-ktx",
        "com.google.firebase:firebase-analytics-ktx",
    )
}
