// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.5.10")
    val compose_version by extra("1.0.0-beta08")
    val dagger_version by extra("2.36")
    val okhttp_version by extra("4.9.0")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-beta03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        classpath("com.google.dagger:hilt-android-gradle-plugin:$dagger_version")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}