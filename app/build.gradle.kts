@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dev.shreyaspatil.compose-compiler-report-generator")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}


android {

    namespace = "com.ogzkesk.safesms"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ogzkesk.safesms"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    hilt {
        enableAggregatingTask = true
    }

}

dependencies {

    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:home"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:contact"))

    implementation(libs.core)
    implementation(libs.activity.compose)
    implementation(libs.material3)

    implementation(libs.hilt)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    implementation(libs.livedata.ktx)
    implementation(libs.viewmodel.ktx)
    implementation(libs.runtime.ktx)
    implementation(libs.runtime.compose)
    implementation(libs.viewmodel.compose)
    implementation(libs.navigation.compose)

    implementation(libs.foundation)
    implementation(libs.ui)
    implementation(libs.ui.tooling)
    implementation(libs.icon.pack)
    implementation(libs.animation)

    implementation(libs.system.ui.controller)

    implementation(libs.analytics)
    implementation(libs.crashlytics)

    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    implementation(libs.timber)

    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)


}