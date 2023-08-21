buildscript {

    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.firebase.crashlytics.gradle)
        classpath(libs.google.services)
        classpath(libs.compose.report)
    }
}

plugins {

    id("com.android.application") version("7.4.2") apply false
    id("com.android.library") version("7.4.2") apply false
    id("org.jetbrains.kotlin.android") version("1.8.21") apply false
    id("org.jetbrains.kotlin.jvm") version("1.8.21") apply false
    id("com.google.gms.google-services") version("4.3.15") apply false
    id("com.google.firebase.crashlytics") version("2.9.8") apply false

}