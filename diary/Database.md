# Database

## Setting up database for the app

_Tuesday, Oct 10th 2023_

- After discussing in-app database options with ChatGPT, I ended up
  selecting plain old [SQLite](https://www.sqlite.org/index.html)
  with [SQLDelight](https://github.com/cashapp/sqldelight) on top of
  it, because of it's maturity and usage base. I also considered
  [Realm](https://www.mongodb.com/docs/realm/sdk/kotlin/) because it
  should also be supported in multiplatform, but ended up with SQLite
  because of my personal familiarity with SQL.
- Started integrating SQLDelight with their
  [instructions](https://cashapp.github.io/sqldelight/2.0.0/multiplatform_sqlite/).
- Found a [Todometer-KMP](https://github.com/serbelga/Todometer-KMP)
  project that had a comprehensive multi-platform setup including
  SQLDelight, so that can be used as an example.
- Theres also this guide:
  [Create a multiplatform app using Ktor and SQLDelight – tutorial](https://kotlinlang.org/docs/multiplatform-mobile-ktor-sqldelight.html)
- Surprisingly, integrating the database went rather smoothly. The
  instructions specify that you should place the database definition
  files (`*.sq`) files under `src/main/sqldelight/your/package/path`,
  but using `src/commonMain/sqldelight/your/package/path` works as
  well.
- Initially, I thought I had the SQLDelight library setup incorrectly,
  as Android Studio could not resolve the links to the generated
  database files, but it turns out that just syncing the Gradle build
  files does not generate those files, but rather you need to run a
  project build (or try starting the app).
- Argh! iOS integration did not work out-of-the-box after all, but
  rather iOS linking is failing:
  ```
  ld: Undefined symbols:
  _sqlite3_bind_blob, referenced from:
      _co_touchlab_sqliter_sqlite3_sqlite3_bind_blob_wrapper69 in shared[arm64][2](result.o)
  ...
  ```

_Wednesday, Oct 11th 2023_

- Had problems trying to link the SQLite library. Initially I tried
  to manually add a `-lsqlite3` linker option, since that seemed to
  be present in some of the example projects, and ChatGPT suggested
  adding that since I was getting linker errors for missing sqlite
  functions, but when I tried to add the option, I started getting
  mysterious Gradle errors such as:
  ```
  Consumable configurations with identical capabilities within a project (other than the default configuration) must have unique attributes, but configuration ':shared:podDebugFrameworkIosFat' and [configuration ':shared:debugFrameworkIosFat'] contain identical attribute sets. Consider adding an additional attribute to one of the configurations to disambiguate them.
  ```
- After asking ChatGPT for help, showing it my Gradle files, I got the
  impression that the cocoapods plugin might be the cause of these.
  At least ChatGPT thought that the `native-cocoapods` plugin would
  be the one that creates those targets that were mentioned.
- Decided that it would be better to try to do this without cocoapods,
  since that is also the direction Compose Multiplatform is moving
  towards.
- Reverted the cocoapods addition, replaced the MOKO resources build
  script with the non-cocoapods one, and rebuilt the project. I only
  needed to clear the XCode derived data
  (`~/Library/Developer/Xcode/DerivedData`) to get the build working
  again (without SQLDelight, that is)!
  So MOKO resources did work without that, and the problems I had
  previously were only due to the missing build script.
- Still could not get the sqlite library linked to the project, until
  ChatGPT told me to make sure I've added the sqlite library as a
  linked library. Now we're getting somewhere! I told ChatGTP that
  I have not done that and asked it how to do it, and got clear
  instructions how to add the library from XCode and voilà, it started
  working. This is my interaction with ChatGTP:

  Prompt:
  > I don't think I've added libsqlite3 to the iOS project. How do I
  do that? Do I need to download it manually or can I add it somehow
  from XCode?
  
  ChatGPT:
  > You don't need to download libsqlite3 manually; it's included with
  the iOS SDK. Here's how you can add it to your iOS project using
  Xcode:
  >
  > 1. Open Your Xcode Project: Launch Xcode and open your iOS project.
  > 1. Select Your Target: In the Project Navigator on the left, select
  the top-level project item. In the main pane, you'll see a list of
  targets. Select your main app target.
  > 1. Go to Build Phases: In the tab bar at the top of the main pane,
  select "Build Phases".
  > 1. Link Binary With Libraries: Expand the "Link Binary With Libraries"
  section. Here, you'll see a list of libraries and frameworks that are
  currently linked with your app.
  > 1. Add libsqlite3: Click the "+" button below the list of libraries.
  This will open a dialog where you can choose a library or framework
  to add. In the search bar, type "sqlite3". You should see
  libsqlite3.tbd appear in the list. Select it and click "Add".
  > 1. Build Your Project: After adding the library, try building your
  project again. The linker error related to SQLite should be resolved.
  >
  >That's it! By following these steps, you'll link your iOS app with the SQLite library, which should resolve the linker errors you were seeing.
- So I guess for iOS developers this could have been something that is
  immediately apparent but for me I would have liked that the
  SQLDelight
  [setup guide](https://cashapp.github.io/sqldelight/2.0.0/multiplatform_sqlite/)
  would have mentioned that you need to manually link the sqlite3
  library to the iOS XCode project. Well, it works now!

## Integrating DB with UI

_Wednesday, Oct 11th 2023_

- Now that the library integrations are done, I decided to do some
  integration to the database contents to be able to test the database
  from the app.
- Asked ChatGPT how to read data from database, how to maintain state
  in Compose, and how to add items to database, and got clear
  instructions that worked out-of-the-box. The only issue I had was my
  own type: I accidentally tried to use a `mutableListOf` instead of
  `mutableStateListOf` to hold the UI state for the drinks list.
  Code generated by ChatGPT was correct, I had just used from
  autocomplete from IDE when integrating changes.
- Populated home screen contents from DB, allowed new items to be
  added and old ones deleted - this took like 15 minutes including
  querying ChatGPT for details and how to do this in Compose + some
  extra time for debugging state update issue (due to the list problem
  mentioned above).
- Using Compose looks to be quite nice (:juicy:)!
