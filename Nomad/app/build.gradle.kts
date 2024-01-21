plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.nomad"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nomad"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androi" +
                "dx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.smarteist:autoimageslider:1.3.9")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
//    implementation      ("com.github.pratikbutani:MultiSelectSpinner:1.0.1")
    implementation      ("com.github.puskal-khadka:MultiSelectSpinner:1.0.1")
    implementation ("org.json:json:20171018")
    implementation("org.osmdroid:osmdroid-android:6.1.17")
    implementation ("com.github.prolificinteractive:material-calendarview:+")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.auth0.android:jwtdecode:2.0.2")
    implementation ("org.mapsforge:mapsforge-core:0.20.0")
    implementation ("org.mapsforge:mapsforge-map:0.20.0")
    implementation ("org.mapsforge:mapsforge-map-reader:0.20.0")
    implementation ("org.mapsforge:mapsforge-themes:0.20.0")
    implementation ("org.mapsforge:mapsforge-map-android:0.20.0")
    implementation ("com.caverock:androidsvg:1.4")
    implementation ("com.github.MKergall:osmbonuspack:6.9.0")
    implementation ("org.java-websocket:Java-WebSocket:1.5.4")
//    implementation ("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")
//    implementation ("org.springframework.android:spring-android-websocket:2.0.0.M3")
//    implementation ("org.springframework:spring-messaging:5.3.10")
//    implementation ("org.springframework:spring-websocket:5.3.10")
    implementation("dev.gustavoavila:java-android-websocket-client:2.0.2")
    implementation ("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("com.github.isradeleon:Notify-Android:1.0.5")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

}
