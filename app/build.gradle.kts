import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    alias(libs.plugins.android.application)
    id("jacoco")
}

android {
    namespace = "com.example.vill0990_a1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vill0990_a1"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
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

    testOptions {
        unitTests.all {
            it.extensions.configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = listOf("jdk.internal.*")
            }
        }
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
    androidTestImplementation(libs.espresso.intents)
    testImplementation(libs.mockito.core)
}

//Combined testing report
afterEvaluate {
    tasks.named("createDebugCoverageReport") {
        dependsOn("connectedDebugAndroidTest")
    }
}

afterEvaluate {
    tasks.named("createDebugCoverageReport") {
        dependsOn("connectedDebugAndroidTest")
    }
}

tasks.create<JacocoReport>("combinedCoverageReport") {
    group = "verification"
    description = "Combines coverage from unit and instrumentation tests"

    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/combinedCoverageReport/html"))
    }

    val fileFilter = listOf(
        "**/R.class", "**/R\$*.class", "**/BuildConfig.*",
        "**/Manifest*.*", "**/*Test*.*", "**/*.jar", "android/**/*.*"
    )

    val javaClasses = fileTree(layout.buildDirectory.dir("intermediates/javac/debug/compileDebugJavaWithJavac/classes")) {
        exclude(fileFilter)
        include("**/*.class")
    }

    classDirectories.setFrom(files(javaClasses))
    sourceDirectories.setFrom(files("src/main/java"))

    val coverageFiles = files(
        layout.buildDirectory.file("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"),
        layout.buildDirectory.file("outputs/code_coverage/debugAndroidTest/connected/Pixel_4a(AVD) - 16/coverage.ec")
    )

    executionData.setFrom(coverageFiles)

    doFirst {
        println("Checking class files...")
        javaClasses.forEach {
            println(" - Java class: ${it.absolutePath}")
        }

        println("Coverage files found:")
        coverageFiles.files.filter { it.exists() }.forEach {
            println(" - ${it.absolutePath}")
        }
    }

    outputs.upToDateWhen { false }
}






