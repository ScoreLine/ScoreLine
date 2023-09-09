@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.swayy.footballpro"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.swayy.footballpro"
        minSdk = 24
        targetSdk = 33
        versionCode = 10
        versionName = "1.0.9"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "license/README.dom.txt"
            excludes += "license/LICENSE.dom-documentation.txt"
            excludes += "license/NOTICE"
            excludes += "license/*"
            resources.excludes.add("META-INF/*")
            excludes += "META-INF/*"
            excludes += "/META-INF/DEPENDENCIES"
            resources.excludes.add("META-INF/**/*")
            resources.excludes.add("META-INF/notice.txt")
            resources.merges.add("META-INF/LICENSE")
            resources.merges.add("META-INF/AL2.0")
            resources.merges.add("META-INF/LGPL2.1")
        }
    }

}

dependencies {

    implementation(project(":core"))
    implementation(project(":core-network"))
    implementation(project(":compose-ui"))
    implementation(project(":feature:matches"))
    implementation(project(":feature:news"))
    implementation(project(":feature:more"))
    implementation(project(":feature:standings"))
    implementation(project(":feature:favourites"))
    implementation(project(":feature:shared"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //compose lifecycle
    implementation(libs.compose.lyfecycle)

    //compose util
    implementation(libs.compose.util)

    //navigation
    implementation(libs.compose.navigation)
    implementation(libs.accompanist.animation)

    implementation(libs.accompanist.pager) // Pager
    implementation(libs.accompanist.pager.indicators) // Pager Indicators

    // System UI Controller - Accompanist
    implementation(libs.accompanist.systemuicontroller)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    // when using Kotlin
    kapt(libs.hilt.compiler)

    //datastore
    implementation(libs.datastore)

    //material icons
    implementation(libs.material.icons)

    //support library
    implementation(libs.appcompat)

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("com.google.android.gms:play-services-ads:22.2.0")

    implementation ("com.google.ads.mediation:facebook:6.15.0.0")

}