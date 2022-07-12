object ApplicationId {
    const val id = "com.example.razorpayifsc"
}

const val revisionPrefix = "rev"
const val conjuctionPrefix = "-"
const val pointPrefix = "."

object Release {
    private const val versionMajor = 1
    private const val versionMinor = 2
    private const val buildNum = 0

    const val versionCode = versionMajor * 1000000 + versionMinor * 1000 + buildNum

    private val versionName = StringBuilder()
        .append(versionMajor)
        .append(pointPrefix)
        .append(versionMinor)
        .append(pointPrefix)
        .append(buildNum)
        .toString()

    private const val revisionCode = 1

    val versionNameDev = StringBuilder()
        .append(versionName)
        .append(conjuctionPrefix)
        .append(revisionPrefix)
        .append(conjuctionPrefix)
        .append(revisionCode).toString()

    const val compileSdkVersion = 31
    const val targetSdkVersion = 31
    const val minSdkVersion = 21

}

object Config {
    const val gradle = "com.android.tools.build:gradle:7.0.4"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltCoreVersion}"
    const val navigationGradle =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigationVersion}"
    const val googleGradle =
        "com.google.gms:google-services:${Version.googleVersion}"
    const val firebaseCrashlyticsGradle =
        "com.google.firebase:firebase-crashlytics-gradle:${Version.firebaseCrashlyticsVersion}"
}

object Version {
    // Kotlin based
    const val kotlinVersion = "1.5.20"
    const val kotlinCoreVersion = "1.8.0"
    const val hiltCoreVersion = "2.42"
    const val googleVersion = "4.3.10"
    const val firebaseCrashlyticsVersion = "2.9.0"

    // LifecycleComponents
    const val lifecycleComponentVersion = "2.5.0"
    const val lifecycleVersion = "2.2.0"

    //json
    const val gsonVersion = "2.8.6"

    //Rx
    const val rxAndroid = "1.2.0"
    const val rxJava = "1.1.8"
    const val rxJava3 = "3.0.0"

    //Dependency Injection
    const val hilt = "2.38.1"

    //image
    const val glideVersion = "4.9.0"

    //Networking
    const val retrofitVersion = "2.9.0"
    const val okhttpLoggingVersion = "4.9.1"

    //OkHttp
    const val httpVersion = "4.9.1"

    //Android jetpack
    const val appcompatVersion = "1.4.2"
    const val constraintLayoutVersion = "2.1.4"
    const val navigationVersion = "2.4.2"
    const val materialComponentVersion = "1.6.1"
    const val legacySupportVersion = "1.0.0"
    const val fragmentVersion = "1.5.0"
    const val splashVersion = "1.0.0-rc01"

    //coroutines
    const val coroutines = "1.5.2"
    const val coroutinesAdapter = "0.9.2"

    //test
    const val testRunnerVersion = "1.4.0"
    const val junitVersion = "4.13.2"
    const val junitExtVersion = "1.1.3"
    const val espressoVersion = "3.4.0"
    const val mockWebVersion = "4.9.1"
    const val testVersion = "1.4.0"
    const val archCoreVersion = "2.1.0"

    //Firebase crashlytics
    const val crashlyticsVersion = "18.2.11"

    //firebase
    const val firebasePerfVersion = "19.0.0"
    const val firebaseAnalytics = "17.2.0"
    const val firebaseBom = "30.1.0"

    //unit test
    const val mockitoVersion = "2.21.0"
    const val mockitoInlineVersion = "4.4.0"
    const val mockitoKotlinVersion = "2.1.0"
    const val mockitoAndroidVersion = "4.6.1"
    const val coroutineTestVersion = "1.4.2"
    const val hiltAndroidTestVersion = "2.42"

    //shadow
    const val fragmentTestVersion = "1.4.1"
    const val coreTestVersion = "1.1.1"
    const val hiltTestVersion = "2.38.1"

    // Lottie animation
    const val lottieAnimationVersion = "5.2.0"

    // MockK
    const val mockKVersion = "1.12.4"

}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinVersion}"
}

object Hilt {
    const val  android = "com.google.dagger:hilt-android:${Version.hilt}"
    const val  hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
}

object Support {
    const val core = "androidx.core:core-ktx:${Version.kotlinCoreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appcompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
    const val materialComponent =
        "com.google.android.material:material:${Version.materialComponentVersion}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Version.legacySupportVersion}"
}

object LifecycleComponents {

    const val lifeCycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleVersion}"

    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleComponentVersion}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleComponentVersion}"
    const val liveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycleComponentVersion}"
    const val materialComponent =
        "com.google.android.material:material:${Version.materialComponentVersion}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Version.legacySupportVersion}"
}

object Arch {
    const val fragment =
        "androidx.fragment:fragment-ktx:${Version.fragmentVersion}"
    const val splash =
        "androidx.core:core-splashscreen:${Version.splashVersion}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigationVersion}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigationVersion}"
}

object Coroutine {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.coroutinesAdapter}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.okhttpLoggingVersion}"
}

object OkHttp {
    const val http = "com.squareup.okhttp3:okhttp:${Version.httpVersion}"
    const val urlConnection = "com.squareup.okhttp3:okhttp-urlconnection:${Version.httpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.httpVersion}"
}

object Json {
    const val gson = "com.google.code.gson:gson:${Version.gsonVersion}"
}

object RxJava {
    const val rxAndroid = "io.reactivex:rxandroid:${Version.rxAndroid}"
    const val rxJava = "io.reactivex:rxjava:${Version.rxJava}"
    const val rxJava3 = "io.reactivex.rxjava3:rxjava:${Version.rxJava3}"
}

object TestLibs {
    const val junit = "junit:junit:${Version.junitVersion}"
    const val junitExt = "androidx.test.ext:junit:${Version.junitExtVersion}"
    const val testRunner = "androidx.test:runner:${Version.testRunnerVersion}"
    const val testRules = "androidx.test:rules:${Version.testRunnerVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espressoVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebVersion}"
    const val testCore = "androidx.test:core:${Version.testVersion}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Version.archCoreVersion}"
    const val mockitoCore = "org.mockito:mockito-core:${Version.mockitoVersion}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockitoInlineVersion}"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockitoKotlinVersion}"
    const val mockitoAndroid =
        "org.mockito:mockito-android:${Version.mockitoAndroidVersion}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutineTestVersion}"
    const val hiltAndroidTest =
        "com.google.dagger:hilt-android-testing:${Version.hiltAndroidTestVersion}"

    const val hiltCompiler =
        "com.google.dagger:hilt-compiler:${Version.hiltAndroidTestVersion}"
    const val fragmentTest = "androidx.fragment:fragment-testing:${Version.fragmentTestVersion}"
    const val hiltTest = "com.google.dagger:hilt-android-compiler:${Version.hiltTestVersion}"
    const val coreTesting = "android.arch.core:core-testing:${Version.coreTestVersion}"
}

object Firebase {
    const val crashlytics = "com.google.firebase:firebase-crashlytics:${Version.crashlyticsVersion}"
    const val performance = "com.google.firebase:firebase-perf:${Version.firebasePerfVersion}"
    const val analytics = "com.google.firebase:firebase-analytics:${Version.firebaseAnalytics}"
    const val bom = "com.google.firebase:firebase-bom:${Version.firebaseBom}"
}

object LottieAnimation {
    const val lottie = "com.airbnb.android:lottie:${Version.lottieAnimationVersion}"
}

object MockK {
    const val mockK = "io.mockk:mockk:${Version.mockKVersion}"
    const val mockKAndroid = "io.mockk:mockk-android:${Version.mockKVersion}"
}