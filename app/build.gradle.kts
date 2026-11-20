import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Firebase plugin
    alias(libs.plugins.google.gms.google.services)
}

// Android configuration
android {
    namespace = "com.example.travelmate"
    compileSdk = 36

    // App settings
    defaultConfig {
        applicationId = "com.example.travelmate"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Maps API key
        val properties = Properties()
        val localFile = rootProject.file("local.properties")

        if (localFile.exists()) {
            localFile.inputStream().use {
                properties.load(it)
            }
        }

        val mapsKey =
            properties.getProperty("MAPS_API_KEY", "")

        // API key setup
        manifestPlaceholders["MAPS_API_KEY"] = mapsKey
        buildConfigField(
            "String",
            "MAPS_API_KEY",
            "\"$mapsKey\""
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

    // Java compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // Core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Unit testing
    testImplementation(libs.junit)

    // UI testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Firebase authentication
    implementation(libs.firebase.auth)

    // Material icons
    implementation("androidx.compose.material:material-icons-extended")

    // Places SDK
    implementation(libs.places)

    // Maps Compose
    implementation(libs.maps.compose)

    // Firebase platform
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.firebase.firestore)

    // Maps utilities
    implementation("com.google.maps.android:maps-ktx:5.1.1")
}