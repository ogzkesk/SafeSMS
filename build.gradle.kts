buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.gradle.plugin)
    }
}

plugins {

    id("com.android.application") version("8.1.0") apply false
    id("com.android.library") version("8.1.0") apply false
    id("org.jetbrains.kotlin.android") version("1.9.0") apply false
    id("org.jetbrains.kotlin.jvm") version("1.9.0") apply false
}