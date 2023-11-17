# Kaljakello (Beer Clock)

This repo contains the implementation of a multiplatform (Android + iOS)
mobile app for tracking blood alcohol concentration based on recorded
alcohol consumption. It can be used to give you an estimate for how long
it takes for your body to clear out the remaining alcohol. Please note
that the app can only calculate an estimation based on the generally
available alcohol calculation formulas. For more accurate results you
must use a proper blood alcohol measuring tool.

This project is based on the Jetpack Compose Multiplatform project, and
it has been created from their
[project template](https://github.com/JetBrains/compose-multiplatform-template).

<p align="center" width="100%">
<img src="beertime.jpeg" alt="Beer time" width="500" /><br />
<i>Illustration courtesy of DALL-E 3 via Bing<br />
Prompt: dark and moody image of a beer glass as a clock with the time slowly ticking away</i>
</p>

Read the [project diary](./diary/README.md) for some description in
how the project was setup.

## Troubleshooting

See the [troubleshooting](./diary/Troubleshooting.md) documentation for
common errors and some workarounds when developing with Compose
Multiplatform.


### TODO:

Deleted `androidApp/build.gradle.kts`:

```gradle
plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "fi.tuska.beerclock"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "fi.tuska.beerclock.BeerClock"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    androidResources {
        generateLocaleConfig = true
    }
    dependencies {
        implementation("androidx.startup:startup-runtime:1.1.1")
    }
    kotlin {
        jvmToolchain(17)
    }
}
```