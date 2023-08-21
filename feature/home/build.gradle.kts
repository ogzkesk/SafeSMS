@file:Suppress("UnstableApiUsage")


plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("dev.shreyaspatil.compose-compiler-report-generator")
    id("kotlin-kapt")
}


android {
    namespace = "com.ogzkesk.home"
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

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

    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    implementation(libs.coil)
    implementation(libs.timber)
    implementation(libs.tflite.text)


}