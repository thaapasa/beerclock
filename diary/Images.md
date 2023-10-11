# Adding images

_Monday, Oct 9th 2023_

- Tried adding custom images on the app and quickly realized you need
  platform-specific functionality for packaging and displaying images.
- Found the [MOKO resources](https://github.com/icerockdev/moko-resources)
  library that seems to be a de-facto standard used with Kotlin
  Multiplatform. Tried installing it but failed sinced it required
  changes to Podfile setup.
- Switched to installing [Cocoapods](./Cocoapods.md) to be able to
  make the required changes.
- Integrating MOKO resources was not quite straightforward. The
  installation instructions cover a wide variery of use cases, with
  no clear examples of how to integrate it to a Compose Multiplatform
  project:
  - Gradle setup instructions can't be followed blindly - if you 
    add the `appprojects` repository setup it will override the current
    repository setup and you will lose access to other repositories,
    making the build fail.
- When trying out the integration, the build would invariable fail due
  to some configuration being wrong, causing Gradle to become corrupted
  and needing a lot of time spend debugging Gradle problems. See
  [troubleshooting](./Troubleshooting.md) for more details.
- Found instructions from other projects:
  - [From Android to Multiplatform: Real 100% Jetpack Compose App](https://markonovakovic.medium.com/from-android-to-multiplatform-real-100-jetpack-compose-app-part-1-resources-a5db60f1ed73)
  - [Compose Multiplatform Moko resource integration](https://medium.com/@boobalaninfo/article-1-compose-multiplatform-moko-resource-integration-dbccbf19aab7)
- Finally got the integration working, after adding the missing build
  script to [XCode project file](../iosApp/iosApp.xcodeproj/project.pbxproj).

_Tuesday, Oct 10th 2023_

- I managed to get the build working by following the instructions from
  the [Medium article](https://medium.com/@boobalaninfo/article-1-compose-multiplatform-moko-resource-integration-dbccbf19aab7),
  but it seems that the build phase setup was actually the same as what
  was instructed for a non-cocoapods installation in the
  MOKO resources [readme](https://github.com/icerockdev/moko-resources#without-orgjetbrainskotlinnativecocoapods).
  I replaced it with the cocoapods-version of the script, and, well, it
  works tool. Wonder why the non-cocoapods version worked as well?
- The problem for me was that the instructions in the MOKO readme
  assumed that I know more about the project setup than I actually do.
  The title is now _iOS/macOS static kotlin frameworks support_,
  and I was unsure whether that step was relevant to the Compose
  Multiplatform project I am creating. Seems to be.
- Also: noticed that [this issue](https://github.com/JetBrains/compose-multiplatform/issues/3553)
  discusses resource management and cocoapods for Compose Multiplatform.
  It seems that Compose Multiplatform is trying to move away from
  cocoapods, which might explain why the project examples do not
  include the cocoapods setup. But on the other hand I could not find
  examples of how to access the resources without the MOKO library,
  and adding that seems to require cocoapods. I'm not 100% certain of
  this, maybe it would have worked by following the MOKO resource
  instructions for the non-cocoapods version.
