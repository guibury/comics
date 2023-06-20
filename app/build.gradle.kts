import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

android {
    compileSdk = 33

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        applicationId = "com.guilhermebury.comics"
        namespace = "com.guilhermebury.comics"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.guilhermebury.comics.espresso.runner.EspressoRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildTypes {
        fun getEnvValue(key: String): String {
            return gradleLocalProperties(rootDir).getProperty(key) ?: System.getenv(key).orEmpty()
        }
        val apiKey = getEnvValue("API_KEY")
        val ts = getEnvValue("TS")
        val hash = getEnvValue("HASH")
        val baseUrl = getEnvValue("BASE_URL")

        debug {
            buildConfigField("String", "API_KEY", apiKey)
            buildConfigField("String", "TS", ts)
            buildConfigField("String", "HASH", hash)
            buildConfigField("String", "BASE_URL", baseUrl)
        }

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_KEY", apiKey)
            buildConfigField("String", "TS", ts)
            buildConfigField("String", "HASH", hash)
            buildConfigField("String", "BASE_URL", baseUrl)
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    androidTestImplementation(project(mapOf("path" to ":app")))

    val lifecycleVersion = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    val koinVersion = "3.4.0"
    implementation("io.insert-koin:koin-core:${koinVersion}")
    implementation("io.insert-koin:koin-android:${koinVersion}")
    implementation("io.insert-koin:koin-test:${koinVersion}")
    implementation("io.insert-koin:koin-test-junit5:${koinVersion}")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("com.google.android.material:material:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    val junit5Version = "5.8.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junit5Version}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junit5Version}")

    val espressoVersion = "3.5.1"
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${espressoVersion}")
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:${junit5Version}")
    androidTestImplementation("io.mockk:mockk-android:1.11.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")


    implementation("com.github.bumptech.glide:glide:4.15.1")
}