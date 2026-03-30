import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.starsgallery"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.starsgallery"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dépendance pour utiliser le RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // Dépendance pour afficher des images circulaires facilement
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Dépendance de la librairie Glide pour charger les images depuis internet
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Processeur d'annotations pour Glide (Syntaxe Kotlin DSL)
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
}