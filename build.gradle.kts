// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.protobuf.plugin) apply false
    alias(libs.plugins.room.plugin)
}

buildscript{
    dependencies{
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.protobuf.gradle.plugin)
        classpath(libs.secrets.gradle)
    }
}