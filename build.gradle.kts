lateinit val gradle_version: String
lateinit var kotlin_version: String
lateinit var hilt_version: String
lateinit var room_version: String

// Top-level build file where you can add configuration options common to all sub-projects/modules.



// Define versions at the top-level for accessibility
ext {
   var kotlin_version = "1.9.22"
   var gradle_version = "8.3.1"
   var hilt_version = "2.49"
    var room_version = "2.6.1"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:$gradle_version")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        // Note: Application dependencies should be in the module build.gradle
    }
}

plugins {
    id ("com.android.application" version  "$gradle_version" apply false)
    id ("com.android.library" version "$gradle_version" apply false)
    id ("org.jetbrains.kotlin.android" version "$kotlin_version" apply false)
    id ("com.google.devtools.ksp" version "1.9.22-1.0.17" apply false)
}
