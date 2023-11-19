# Upgrading the baseline

## Setting up database for the app

_Thursday, Nov 16th 2023_

- Having done something else for a while, I thought I'd pop back in to get
  some development done. I soon remembered that I wanted to take the
  Material 3 library into use, and started looking into how to replace the
  baseline Material library with the v3.
- I quickly found out that a stable release of the Compose Multiplaform has
  now been made:
  [Compose Multiplatform 1.5.10 released](https://blog.jetbrains.com/kotlin/2023/11/compose-multiplatform-1-5-10-release/)
- Started working on upgrading the project to the stable project structure.
  There is now a
  [Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/)
  that can be used to generate the baseline project structure. Nice!

_Saturday, Nov 18th 2023_

- Continued upgrading the project to match the new structure. A lot has
  changed, so this again needs a lot of trial and error to get things
  working.
- I could not get Moko resources working again, and indeed there seems
  to be an
  [open issue](https://github.com/icerockdev/moko-resources/issues/590)
  stating that the new version is not supported (actually the previous
  version this project used should not have been supported but that worked
  - go figure).
- However, it seems that Compose Multiplaform supports cross-platform
  images after all: there's a note about that mentioned somewhere
  [here](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-new-project.html#introduce-images),
  but I could not find any more specifications about resource handling.
- Ended up dropping Moko resource plugin and libraries from the project
  since the internal resource loader seems to work. You just have to
  use bitmaps or Android XML files (not SVGs), like in Android projects.
  Luckily Android Studio can convert SVGs to Android's XML.
