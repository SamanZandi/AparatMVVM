// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io")  }
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)

    }
}