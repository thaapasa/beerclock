# Conclusion

- Compose for Android seems to be mature, and has a lot of
  documenation.
  - Not so for Multiplatform. You'll find documentation and libraries
    for Compose and then be disappointed to find out they're not for
    Multiplatform.
- Very bleeding edge - there's some documentation, but a lot of it is
  contradictory and outdated as the platform keeps changing so fast.
  Compose Multiplatform seems to be moving away from cocoapods for iOS
  and that means that new templates do not have cocoapods but all the
  example projects found from the net do and all the instructions for
  using libraries assume you have cocoapods.
- Getting things working is like upgrading React Native with lots of
  native dependencies - be prepared to debug mysterious build problems
  and scour the net for hard-to-find information.
- Even the libraries specifically designed to Compose Multiplatform
  might not work. The iOS support especially is poor.
- Lots of suggested libraries are in alpha or release candidate stage.
- Gradle caches need to be constantly manually invalidated - the
  project just stops building and throws random errors unrelated to
  anything that's in the code.

## Comparison against React Native

Pros React Native:

- React Native is a lot easier to approach, and has a much more mature
  ecosystem.
- Updating React Native versions can be a hassle, but just setting up
  a working Compose Multiplatform project with any extensions (such as
  just having images in the app) is even more so.
- Development cycle is faster in React Native, when you can hot reload
  JS changes directly to the mobile emulator/simulator.

Pros of Compose Multiplatform:

- You get the Material UI library (at least parts of it)
  out-of-the-box.
- You can use Kotlin, a strongly typed but still flexible language.
- I assume performance will be better, since we are creating "native"
  code, without the JS layer. For Android, the performance should be
  as good as any other native Android app. No experiences from this
  yet though.

Other notices:

- Rendering elements in Compose feels a bit like creating components
  in React, at least superficially.
- Lot of the problems in setting up the project seem to be related to
  the iOS platform being in the alpha stage. Compose Multiplatform
  might be a lot more appealing option once the iOS platform support
  matures.
