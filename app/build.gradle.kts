plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "lk.evenro.even"
    compileSdk = 35

    defaultConfig {
        applicationId = "lk.evenro.even"
        minSdk = 24
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.code.gson:gson:2.11.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.material:material:1.6.0")
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.android.gms:play-services-maps:19.0.0")


    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation ("com.google.firebase:firebase-auth:22.1.1")
    implementation ("com.google.android.gms:play-services-auth:20.6.0")


    implementation ("com.github.PayHereDevs:payhere-android-sdk:v3.0.17")


    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.cloudinary:cloudinary-android:3.0.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}