# Project setup diary

This diary records setting up the project and learning about the
current bleeding edge libraries for multiplatform app development
with Jetpack Compose Multiplatform.

<p align="center" width="100%">
<img src="./frustration.jpeg" alt="Somewhat frustrating" width="500" /><br/>
<i>Spoiler: it can be somewhat frustrating</i>
</p>

Note: This project was initially bootstrapped from the
[all-platforms template](https://github.com/JetBrains/compose-multiplatform-template).
However, once it became apparent that supporting multiple platforms
requires a lot of platform-specific work, I recreated the project based on the
[iOS + Android template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme).
The old project repository is retained
[here](https://github.com/thaapasa/beerclock-multiplatform).

## Development machine

- Apple M1 Max MacBook Pro with 64 GB of memory
- MacOS Sonoma 14.0
- XCode 15.0
- Android Studio Giraffe 2022.3.1 Patch 2
- OpenJDK 17 (from Homebrew, see diary below)

## Links to resources

Compose Multiplatform

- [Project page](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Getting started -guide](https://github.com/JetBrains/compose-multiplatform/#readme)
- [iOS + Android template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme)
- [Multiplatform template](https://github.com/JetBrains/compose-multiplatform-template)
- [Example projects](https://github.com/JetBrains/compose-multiplatform/tree/master/examples)

Jetpack Compose
- [Documentation](https://developer.android.com/jetpack/compose)
- [Material UI components](https://developer.android.com/jetpack/compose/components)

Other resources
- [Awesome Kotlin Multiplatform](https://github.com/terrakok/kmp-awesome#-compose-ui)

## The journey

1. [Setting up basic structure](./Setup.md)
1. [State and navigation](./State%20and%20navigation.md)

## TODO:

- Charts: [AAY-chart](https://github.com/TheChance101/AAY-chart)
