plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    val voyagerVersion = "1.0.0-rc07"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // Voyager is used to provide navigation for the app
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("dev.icerock.moko:resources-compose:0.23.0")
            }
        }
        val androidMain by getting {
            dependencies {
                dependsOn(commonMain)
                api("androidx.activity:activity-compose:1.8.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                // The startup library is used for Android to provide a global reference to the
                // Application Context
                implementation("androidx.startup:startup-runtime:1.1.1")
                implementation("app.cash.sqldelight:android-driver:2.0.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
        }
    }
}

sqldelight {
    databases {
        create("BeerDatabase") {
            packageName.set("fi.tuska.beerclock.common.database")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "fi.tuska.beerclock.common" // required
    multiplatformResourcesClassName = "MR" // optional, default MR
    disableStaticFrameworkWarning = true
    multiplatformResourcesSourceSet = "commonMain"
}

dependencies {
    commonMainApi("dev.icerock.moko:resources:0.23.0")
    commonMainApi("dev.icerock.moko:resources-compose:0.23.0") // for compose multiplatform
    // commonTestImplementation("dev.icerock.moko:resources-test:0.23.0") // for testing
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "fi.tuska.beerclock.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    // This causes a "duplicate content roots detected" error but it is actually needed.
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
