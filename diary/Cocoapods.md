# Cocoapods

## Switching templates

_Mon, Oct 9th 2023_

- I started integrating shared resources library
  [Moko resources](https://github.com/icerockdev/moko-resources)
  but quickly noticed that the iOS setup parts requires making manual
  changes to the iOS Podfile, and the multiplatform template that the
  project is based on does not have any Cocoapods setup at all. Other
  examples of Compose Multiplatform found from the internet do have
  Cocoapods setup, so it should be possible, but why is the
  configuration missing from the base template...?
- After trying to integrate Cocoapods to the project I noticed that
  there was an explicit
  [iOS + Android template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme)
  available. At this point I was struggling with some random errors
  with the Gradle build not finding some files generated for a JVM
  target (such as the desktop version), I thought it best to
  recreate the project so that there are no leftover code still
  pointing to the desktop target because I'm not really interested
  in that.
- So, time to rename the old project to
  [beerclock-multiplatform](https://github.com/thaapasa/beerclock-multiplatform)
  and recreate the project from the correct template.
  Github retains a link to the template used to create the project,
  so leaving a pointer to the all-platforms version would be
  misleading.
- Recreating the project was quite fast now, as there is not much
  code yet (and no more head-banging was needed).

## Integrating cocoapods configuration

_Mon, Oct 9th 2023_

- Next step: integrate CocoaPods to the project. As mentioned before,
  the multiplatform project templates do not contain any cocoapods
  setup, and I could not find any mention of cocoapods configuration
  and why it's not included in the project template from the
  Compose Multiplatform project pages. I guess this is due to iOS
  support only being in alpha. I think the cocoapods configuration is
  required when using just
  [KMM](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html),
  but Compose Multiplatform has build tools that can work without it
  and the project template is kept simpler by leaving the configuration
  out.
- I tried to configure the Cocoapods setup by comparing the project
  build files, Podfiles, etc. to other Compose Multiplatform example
  projects such as:
  - [Appyx navigation example](https://github.com/bumble-tech/appyx/tree/2.x/demos/appyx-navigation)
  - [Example Cocoapods project](https://github.com/JetBrains/compose-multiplatform/tree/master/examples/cocoapods-ios-example)
    from the Compose Multiplatform project
  Also found various blog posts and documentation entries on the topic:
  - [Cocoapods integration instructions](https://kotlinlang.org/docs/native-cocoapods.html)
    for Kotlin/Native
  - [Kotlin Multiplatform by Tutorials](https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/2-getting-started)
    from Kodeco
  - [Compose Multiplatform Movie App](https://piashcse.medium.com/compose-multiplatform-movie-app-4752cd445e95)
  - [Jetpack Compose Multiplatform Android & iOS](https://proandroiddev.com/jetpack-compose-multiplatform-android-ios-4a87ba417caa)
- However, the project build kept failing. Various errors encountered along the way:
  - Gradle loses visibility to basic Kotlin files, build files are
    filled with errors. In this case I had to delete the
    `~/.gradle/caches` directory, clean up the project (`./cleanup.sh`)
    and rebuild everything.
  - iOS target could not find the `Main_iosKt` reference in
    [ContentView.swift](../iosApp/iosApp/ContentView.swift).
    This reference is not defined anywhere, and I just have to assume
    if is automatically generated from the
    [main.ios.kt](../shared/src/iosMain/kotlin/main.ios.kt)
    filename.
- Finally, I tried recreating the changes from scratch, cleaning
  everything before and making sure that the project compiles
  without the cocoapods changes. Surprisingly, now the project
  compiled and ran just fine. I think the problem might be that I
  had used the wrong module name for the shared project in the
  gradle configuration files, and that might have generated some
  intermediate files incorrectly somewhere. Maybe in the Pods
  directory?

