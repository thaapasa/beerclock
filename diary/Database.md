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
