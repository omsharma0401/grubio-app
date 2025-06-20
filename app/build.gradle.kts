import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Dagger Hilt
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")

    // Serialization
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")

}

android {
    namespace = "com.omsharma.grubio"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.omsharma.grubio"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WEB_CLIENT_ID", getWebClientId())
        buildConfigField("String", "FACEBOOK_APP_ID", getFacebookAppId())
        buildConfigField("String", "FACEBOOK_CLIENT_TOKEN", getFacebookClientToken())
        buildConfigField("String", "BASE_URL", getBaseUrl())
        buildConfigField("String", "MAPS_API_KEY", getMapsApiKey())

        manifestPlaceholders.put("facebook_app_id", getFacebookAppId())
        manifestPlaceholders.put("facebook_client_token", getFacebookClientToken())
        manifestPlaceholders.put("fb_login_protocol_scheme", getFbLoginProtocolScheme())
        manifestPlaceholders.put("MAPS_API_KEY", getMapsApiKey())

        resValue("string", "facebook_app_id", getFacebookAppId())
        resValue("string", "facebook_client_token", getFacebookClientToken())
        resValue("string", "fb_login_protocol_scheme", getFbLoginProtocolScheme())
        resValue("string", "maps_api_key", getMapsApiKey())

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
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

    // Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Dagger Hilt
    val hiltVersion = "2.56.2"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Retrofit and JSON Converter
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ViewModel Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Google Login
    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Facebook Login
    implementation("com.facebook.android:facebook-login:18.0.3")

    // Coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    // Google Maps Compose
    implementation("com.google.maps.android:maps-compose:6.4.1")

    // Location
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

}

fun getWebClientId(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("WEB_CLIENT_ID")
}

fun getFacebookAppId(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("FACEBOOK_APP_ID")
}

fun getFacebookClientToken(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("FACEBOOK_CLIENT_TOKEN")
}

fun getFbLoginProtocolScheme(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("FB_LOGIN_PROTOCOL_SCHEME")
}

fun getMapsApiKey(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("MAPS_API_KEY")
}

fun getBaseUrl(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("BASE_URL")
}