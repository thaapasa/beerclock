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

## The journey

1. [Setting up basic structure](./Setup.md)
1. [State and navigation](./State%20and%20navigation.md)
1. [Cocoapods](./Cocoapods.md)
1. [Adding images](./Images.md)
1. ...
1. [Conclusion](./Conclusion.md)

See also:

- [Troubleshooting](./Troubleshooting.md) common errors

## Links to external resources

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

Other projects or blogs regarding Compose Multiplatform projects:

- [Appyx navigation example](https://github.com/bumble-tech/appyx/tree/2.x/demos/appyx-navigation)
- [Cocoapods integration instructions](https://kotlinlang.org/docs/native-cocoapods.html)
  for Kotlin/Native
- [Kotlin Multiplatform by Tutorials](https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/2-getting-started)
  from Kodeco
- [Compose Multiplatform Movie App](https://piashcse.medium.com/compose-multiplatform-movie-app-4752cd445e95)
- [Jetpack Compose Multiplatform Android & iOS](https://proandroiddev.com/jetpack-compose-multiplatform-android-ios-4a87ba417caa)
- [From Android to Multiplatform: Real 100% Jetpack Compose App](https://markonovakovic.medium.com/from-android-to-multiplatform-real-100-jetpack-compose-app-part-1-resources-a5db60f1ed73)
- [Compose Multiplatform Moko resource integration](https://medium.com/@boobalaninfo/article-1-compose-multiplatform-moko-resource-integration-dbccbf19aab7)
- [Resource management and cocoapods issue](https://github.com/JetBrains/compose-multiplatform/issues/3553)

## TODO:

- Charts: [AAY-chart](https://github.com/TheChance101/AAY-chart)
