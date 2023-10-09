# State and navigation

## App state

_Thursday, Oct 5th 2023_

- Moving on to state handling. And example
  [todo app](https://github.com/JetBrains/compose-multiplatform/blob/master/examples/todoapp-lite)
  has some examples of
  [state handling](https://github.com/JetBrains/compose-multiplatform/blob/master/examples/todoapp-lite/shared/src/commonMain/kotlin/example/todoapp/lite/common/RootStore.kt).
  Started creating custom store for storing user settings so I can
  create a settings page for the app.
- ChatGPT was again helpful in reminding how enum classes work in
  Kotlin, what the `internal` visibility modifier means exactly, and
  how to type Composables that take other Composable as parameter
  (some lambda-wrapping was needed because `@Composable` references
  are not supported out-of-the-box).
- Noticed that Compose has a `@Preview` annotation for Composables
  that works with IDE to support showing a preview of the UI inside the
  IDE. But ... it's not supported for Multiplatform 🙄. There are
  [reports](https://slack-chats.kotlinlang.org/t/12111122/do-ide-previews-work-in-a-compose-multiplatform-setup-i-m-no)
  that suggest you can get it working for the Android-only code, but
  if I'm targeting multiplatform that's kinda missing the point.
- Text component is provided out-of-the-box by Compose (and works
  in Multiplatform as well), but couldn't find a standard dropdown
  selector. Googling found
  [this example](https://gist.github.com/snicmakino/297d34e429c078624fde6771064ed6d2?permalink_comment_id=4051239),
  so created a custom selector based on that.
- Storing user preferences is also something that needs to be manually
  handled for each platform. Google provided some
  [examples](https://medium.com/@shmehdi01/shared-preference-in-kmm-kotlin-multiplatform-2bca14214093),
  and ChatGPT also, with a similar approach. However, both of these
  failed to mention the Android-specific problem regarding `Context`s.
  More specifically, when working in the common, shared code, the
  context is not accessible at all, but it is required for some of
  the Android-specific functionality (such as storing preferences
  using `getSharedPreferences()`).
- Tried to work around the `Context` problem by using the
  `@Composable` annotation. This works, since the `Context` can be
  accessed from `@Composable` functions when in Android-specific
  code by using `LocalContext.current`. But then the
  `@Composable`-annotated functions cannot be called from any
  other scope (such as from inside a launched effect, or from a button
  `onClick` handler). And storing user preferences is anyway something
  that should not be a part of the `@Composable` flow.
- Finally gave up and used the late initialization techique described
  [here](https://proandroiddev.com/how-to-avoid-asking-for-android-context-in-kotlin-multiplatform-libraries-api-d280a4adebd2).
  Now the Android library implementation gets the Application context
  on startup, and just uses that directly.

## Navigation

_Thursday, Oct 5th 2023_

- Aha! Compose seems to be built on top of a "single-Activity" mindset.
  It seems that others have also noticed that state handling was a pain
  with the previous model of firing up new Activities and having to
  recreate state from scratch. This is promising!
- Unfortunately again it seems that for Compose
  [navigation](https://developer.android.com/jetpack/compose/navigation)
  is part of the standard library, but for Multiplatform the standard
  library is
  [not working](https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Navigation)
  and you need to use
  [external libraries](https://github.com/terrakok/kmp-awesome#-compose-ui).
- Trying out [Appyx](https://bumble-tech.github.io/appyx/) next for
  navigation.


_Friday, Oct 6th 2023_

- Started integrating Appyx by adding the dependencies
  ```
  implementation("com.bumble.appyx:appyx-navigation:2.0.0-alpha08")
  api("com.bumble.appyx:backstack:2.0.0-alpha08")
  ```
  to the `commonMain` source set. It seems that Appyx is about to
  release the 2.x version of their library, but since their site
  already lists the 2.x version and doesn't state that it should not
  yet be used, I'm gonna try it out even though it's alpha.
- Started creating navigation based on the
  [Quick start guide](https://bumble-tech.github.io/appyx/navigation/quick-start/).
- Noticed that the code samples for the Desktop version seem to be
  out-of-date. No sense in banging my head against the wall here, since
  Desktop is not an interesting target for me.
- Dropped Desktop from the project.
- Noticed that running a full rebuild of the project (Rebuild Project
  from Android Studio) takes a really long time, like 5-10 minutes.
  Gradle also seems to get the caches broken if the Gradle build files
  are changed too much, especially when removing stuff or making
  larger changes to the project setup.
- To fix Gradle build, you may need to do any or all of the following:
  - run cleanup script `./cleanup.sh`
  - clean project with `./gradlew clean`
  - clear user Gradle caches from e.g. `~/.gradle/caches`
  - invalidate IDE caches (Android Studio: File → Invalidate Caches)
- So, it turns out alpha versions of libraries can be uncooperative.
  The appyx library seems promising but after a day of banging my head
  to the wall I could not get the iOS version of the app to build. For
  some reason the `Node` classes from the appyx library could not be
  found when building the iOS app.
- Switched to [Voyager](https://voyager.adriel.cafe/) and the basic
  navigation worked out-of-the-box.

## Random error hunting

_Friday, Oct 6th 2023_

Android Studio kept nagging me about _Duplicate content roots detected_.
I did not feel like getting to know about this detail right now, so
I just dumped the error message to ChatGPT, and pasted it a couple of
my Gradle configuration files. This is an excerpt of ChatGPT's
response:

> Your project setup is for a Kotlin Multiplatform project that targets
  Android and iOS. Let's address the issue regarding the "Duplicate content roots detected".
>
> From your shared build.gradle.kts:

```kotlin
android {
 ...
 sourceSets["main"].resources.srcDirs("src/commonMain/resources")
 ...
}
```
> This configuration tells Android's "main" source set to include
  src/commonMain/resources as its resource directory. However,
  Android Studio recognizes src/commonMain/resources as the
  resources directory for your Kotlin common source set by default.
>
> This overlap between Android's "main" source set and Kotlin's
  "commonMain" source set is the source of your "Duplicate content
  roots detected" error.
>
> To fix it:
> Remove the Overlap in shared/build.gradle.kts: Simply remove or
  comment out the line that adds src/commonMain/resources to Android's
  "main" source set.
>
> ...

Right! That did it. Man I'm glad I don't need to think myself anymore 🤯
