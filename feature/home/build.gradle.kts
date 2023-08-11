plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
}

android {
    namespace = "com.ogzkesk.home"
    compileSdk = libs.versions.compileSdk.get().toInt()
    compileSdkPreview = "UpsideDownCakePrivacySandbox"

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
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)


    implementation(libs.timber)

    annotationProcessor(libs.hilt.compiler)

}