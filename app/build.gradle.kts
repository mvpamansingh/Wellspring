plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("dagger.hilt.android.plugin")
    id ("com.google.devtools.ksp")
    id("com.mikepenz.aboutlibraries.plugin" version "10.5.2")
}
apply plugin: ("com.mikepenz.aboutlibraries.plugin")

android {
    namespace = "com.example.wellspring"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wellspring"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    ksp {
        arg('room.schemaLocation', "$projectDir/schemas")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    lintOptions {
        abortOnError false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("com.google.android.material:material:1.11.0")
    // Android 12+ splash API.
    implementation ("androidx.core:core-splashscreen:1.0.1")
    // Gson JSON library.
    implementation ("com.google.code.gson:gson:2.10.1")
    // OkHttp library.
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    // Coil Image loading library.
    implementation ("io.coil-kt:coil-compose:2.4.0")
    // Room database components.
    implementation ("androidx.room:room-ktx:$room_version")
    ksp ("androidx.room:room-compiler:$room_version")
    androidTestImplementation ("androidx.room:room-testing:$room_version")
    // Dagger - Hilt.
    implementation ("com.google.dagger:hilt-android:$hilt_version")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp ("com.google.dagger:hilt-android-compiler:$hilt_version")
    ksp ("androidx.hilt:hilt-compiler:1.2.0")
    // Jsoup HTML Parser.
    implementation ("org.jsoup:jsoup:1.17.2")
    // Lottie animations.
    implementation ("com.airbnb.android:lottie-compose:4.1.0")
    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // Open Source Libraries Screen.
    implementation ("com.mikepenz:aboutlibraries-core:10.5.2")
    implementation ("com.mikepenz:aboutlibraries-compose:10.5.2")
    // Swipe actions.
    implementation ("me.saket.swipe:swipe:1.2.0")
    // Crash Handler.
    implementation ("cat.ereza:customactivityoncrash:2.4.0")
    // Kotlin reflect API.
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    // Testing components.
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("com.google.truth:truth:1.1.3")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation ("org.mockito:mockito-core:5.3.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // Android testing components.
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    // debug components.
    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
}