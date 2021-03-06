plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "dev.mizoguche.bookmarkcompose"
        minSdk = 29
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-beta01")

    implementation("com.google.accompanist:accompanist-coil:${rootProject.extra["accompanist_version"]}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${rootProject.extra["accompanist_version"]}")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["dagger_version"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_version"]}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_version"]}")
    kaptAndroidTest("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_version"]}")
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["dagger_version"]}")
    kaptTest("com.google.dagger:hilt-compiler:${rootProject.extra["dagger_version"]}")

    // OKHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:${rootProject.extra["okhttp_version"]}"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("ru.gildor.coroutines:kotlin-coroutines-okhttp:1.0")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:${rootProject.extra["lottie_version"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
}