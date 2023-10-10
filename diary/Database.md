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
