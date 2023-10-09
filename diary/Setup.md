# Project setup

## Creating the project template

_Wednesday, Oct 4th 2023_

Getting project template running on all platforms:

- Located Jetpack [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
- Generated project from
  [template](https://github.com/JetBrains/compose-multiplatform-template).
- Installed OpenJDK 21 (and pointed `JAVA_HOME` to it):

  ```sh
  brew install openjdk
  ````

  Although it seems that Android Studio will use the bundled Java 17
  instead. Oh well.
- Installed and run KDoctor:

  ```sh
  brew install kdoctor
  kdoctor
  # and for more details
  kdoctor -v
  ````

- Updated [Android Studio](https://developer.android.com/studio)
  to latest version (Giraffe, 2022.3.1 Patch 2)
- Installed Kotlin Multiplatform Mobile
  [plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile).
- `./gradlew run` from command line threw error:

  ```
  Unknown Kotlin JVM target: 21
  ```

  Okay, so this Compose setup seems to require Java 17.
  Some googling reveals that it might be possible to use Java 21 but
  just target Java 17.
- Installed OpenJDK 17 (and pointer `JAVA_HOME` to it):

  ```sh
  brew install openjdk@17
  ```

- `./gradlew run` works! 🎉
- Installed Android emulator targeting API 34 (Android 14).
- `./gradlew installDebug` works and installs app on emulator. Similarly
  running from Android Studio works.
- Using instructions from
  [template readme](https://github.com/JetBrains/compose-multiplatform-template#readme),
  selected iOS target for iOS app and was able to run app on iOS
  simulator as well!
- Tried changing the text shown on app main screen. There is no hot
  reload, but you have to rebuild and reinstall the apps. For iOS
  this is a bit slow. For Android it can rebuild and restart only
  the activity, so the update is quite fast. Android Studio reports
  that `Apply Changes successfully finished in 36ms` but the emulator
  updated after about a second.
- We have a working app!

## Learning Compose UI

_Wednesday, Oct 4th 2023_

- To get started with Compose, I copy-pasted the example application
  [main class](https://github.com/JetBrains/compose-multiplatform-template/blob/main/shared/src/commonMain/kotlin/App.kt)
  code to ChatGPT+, with this leading text:

  > This is the sample code file from a Jetpack Compose Multiplatform
  template project. Can you go through it line by line and explain what
  the lines mean? I have basic knowledge of Kotlin but not of Compose.

  The results were quite informative, like:

  ```
  @Composable

  The @Composable annotation indicates that the function is a
  composable function. Composable functions can be called and will
  produce UI. This is a core concept in Jetpack Compose.
  ```

  and

  ```
  MaterialTheme {

  This applies the MaterialTheme, which is a part of Jetpack Compose's
  Material Design components. It provides consistent theming for your
  UI components.
  ```

  and

  ```
  var greetingText by remember { mutableStateOf("Hello, World!") }

  Here, a mutable state is being declared for greetingText. remember is
  a function that remembers a value across recompositions.
  mutableStateOf provides a way to hold state that can trigger
  recomposition when changed. The initial value of greetingText is
  "Hello, World!".
  ```
- Had more conversations with ChatGPT and also Compose's own
  documentation and added a standard Material UI menu bar to the app.
  Next step: learn about `suspend` functions and how to open a drawer
  from top menu buttons.
- Noticed that looking at Jetpack Compose documentation can be
  misleading since not everything seems to be available out-of-the-box.
  Using just Jetpack Compose might be simpler, but I'm not ditching
  iOS support just yet.
- Compose Multiplatform did not seem to have all the components readily
  available, so I could not create e.g. the navigation drawer menu
  exactly like the
  [Compose example](https://developer.android.com/jetpack/compose/components/drawer)
  shows. Luckily I found
  [this example](https://www.netguru.com/blog/multiplatform-adaptive-ui)
  for creating UIs for Compose Multiplatform.
- Created openable side menu. Yay! 🙌

## What about localization?

_Wednesday, Oct 4th 2023_

- It seems that localization may not be directly supported, at least in
  a similar way as it's supported for native Android apps.
  The ImageViewer
  [example project](https://github.com/JetBrains/compose-multiplatform/blob/master/examples/imageviewer),
  for example, uses custom
  [localization code](https://github.com/JetBrains/compose-multiplatform/blob/master/examples/imageviewer/shared/src/commonMain/kotlin/example/imageviewer/Localization.kt).
  Some [discussion](https://github.com/JetBrains/compose-multiplatform/issues/425)
  on this topic.
- Created custom
  [localization code](../shared/src/commonMain/kotlin/localization/Strings.kt)
  to the project.
- Added localization customization to Android project
  [configuration](../androidApp/build.gradle.kts):

  ```groovy
  androidResources {
    generateLocaleConfig = true
  }
  ```

  Also added localizations for the project name under Android string
  resources. This was enough to allow changing the project language
  in Android's own app configuraten menu, and it works with the
  custom localization code.