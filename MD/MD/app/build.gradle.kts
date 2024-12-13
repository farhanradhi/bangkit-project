plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.capstone.sampahin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.capstone.sampahin"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField ("String", "BASE_URL", "\"https://firebase-851479113294.asia-southeast2.run.app/\"")
        buildConfigField ("String", "ML_BASE_URL", "\"https://backend-ml-dot-sampahin.et.r.appspot.com/\"")
        buildConfigField ("String", "MAP_BASE_URL", "\"https://places-nearby-851479113294.asia-southeast2.run.app/\"")
        buildConfigField ("String", "CHAT_BASE_URL", "\"https://chatbot-be-dot-sampahin.et.r.appspot.com/\"")
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
        manifestPlaceholders.putAll(
            mapOf("MAPS_API_KEY" to (project.findProperty("MAPS_API_KEY") ?: ""))
        )

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
        buildConfig = true
    }
//    externalNativeBuild {
//        cmake {
//            path = file("src/main/cpp/CMakeLists.txt")
//            version = "3.22.1"
//        }
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.github.glide)

    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.task.vision)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.gson)
    implementation(libs.tensorflow.lite.task.text)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.tensorflow.lite.gpu.delegate.plugin)

    implementation (libs.ucrop)
    implementation(libs.play.services.location)

}