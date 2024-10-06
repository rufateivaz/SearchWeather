plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.sample.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField(
                "String",
                "API_KEY",
                "\"${project.findProperty("API_KEY")}\""
            )
            buildConfigField(
                "String",
                "SEARCH_URL",
                "\"https://api.openweathermap.org/data/2.5/\""
            )
            buildConfigField(
                "String",
                "ICON_URL",
                "\"https://openweathermap.org/img/wn/\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(project(":domain"))
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}