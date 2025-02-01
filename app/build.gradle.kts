plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // Add this line for Compose Compiler
}

android {
    namespace = "com.example.mysimpleapp"  // 🔹 Add this line
    compileSdk = 34

    lint {
        disable.add("MutableCollectionMutableState")
        disable.add("AutoboxingStateCreation")
    }



    defaultConfig {
        applicationId = "com.example.mysimpleapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    testOptions {
        unitTests.isReturnDefaultValues = true  // Allows mock testing
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // 🔹 Update to match JVM target
        targetCompatibility = JavaVersion.VERSION_17 // 🔹 Update to match JVM target
    }

    kotlinOptions {
        jvmTarget = "17" // 🔹 Ensure this matches compileOptions target
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}


dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Jetpack Compose Essentials
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Debugging tools
    debugImplementation("androidx.compose.ui:ui-tooling")

    // ✅ JUnit for Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // ✅ AndroidX Test for Instrumented Tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ✅ Jetpack Compose UI Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // ✅ Required for Compose UI Testing
    debugImplementation("androidx.compose.ui:ui-test-manifest")    // ✅ Needed for Debug Test Manifests

}
