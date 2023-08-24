@file:Suppress("UnstableApiUsage")


plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ogzkesk.mylibrary"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":core"))

    implementation(libs.core)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.data.store)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.timber)
    implementation(libs.data.store)

    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)

}