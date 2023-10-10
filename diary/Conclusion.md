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

## Should I use Compose Multiplatform

... for an Android + iOS app?

Well, possibly not, but that depends.

### Quick and easy app

If you want to get something up and running quickly and easily, better
try your luck with something like [React Native](https://reactnative.dev/)
instead.

### Performant app that feels native to the platform

If you are already familiar with both iOS and Android app development,
and are prepared to spend time in getting the library integrations
working, Compose Multiplatform might be a good option for you.
With Compose Multiplatform you can easily create shared code for both
platforms and also use platform-specific UI and native code whenever
that is required.

The alpha status is still a bit worrying though, so expect migration
issues later on when the platform support is finalized.

### Shared business, native UIs

The Compose Multiplatform uses
[Kotlin Multiplatform for mobile](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)
(KMM) under the hood. KMM is still in beta, but support for it seems
to be much more mature and there are a lot more resources available
for just the KMM part.

If you plan on creating native UIs with
[Compose](https://developer.android.com/jetpack/compose) for Android,
and [SwiftUI](https://developer.apple.com/xcode/swiftui/) for iOS, you
could well check out KMM to share the business logic.

### Just the Android app

If you just target the Android platform, just use the plain
[Jetpack Compose](https://developer.android.com/jetpack/compose).
It's the recommended toolkit for Android and I can't see why you
wouldn't use if for Android development.

### Just the iOS app

Errm well better skip these Kotlin libraries and use whatever you iOS
developers normally use.
