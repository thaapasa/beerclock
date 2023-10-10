# Troubleshooting

This document lists several problems that were encountered during the
development of this project and some workarounds that helped in
resolving the problem.

<p align="center" width="100%">
<img src="./code-piles.jpeg" alt="Piles of code" width="500" /><br/>
<i>What to do if the code starts piling up?</i>
</p>

## Code doesn't compile

First steps in fixing code compilation or bundling problems should
be to clear the project and retry compilation.

Things you can try:

- From Android studio, run `Build` → `Clean Project`.
- Run `./cleanup.sh` from the project root.
  - This also all the generated files and iOS Pods.
- Run `File` → `Sync Project with Gradle Files` from Android Studio
  to run basic intermediate code generation and see that build setup
  is working.
- Try to run the `androidApp` / `iosApp` configurations from Android
  Studio.

## Multiple Gradle daemons

When you run `./gradlew` from the command-line, Gradle uses the JDK
version from your environment `JAVA_HOME`, if you have that set up.
However, Android Studio defaults to using the Gradle (and JDK) that
it's shipped with, causing Android Studio to spawn a different
Gradle daemon that what is used when running from command-line.

Symptoms:

- Whenever you run a Gradle script that uses a different JDK than the
  currently running Gradle daemon, the running Gradle daemon will be
  stopped and a new one will be launched. Thus, command-line Gradle and
  Android Studio will keep shutting down the running Gradle daemon and
  spawning another, which is slow.
- The Gradle daemons also seem to use a different Gradle cache
  directory. I had a situation where the command-line Gradle was
  corrupted (see below), and running Gradle from command-line did not
  work at all, but running Gradle from Android Studio worked just fine.
  This was very confusing.

How to fix:

- In the Giraffe version of Android Studio, browse to
  `File` → `Project Structure` → `SDK Location`, then find the note
  about JDK location being moved, and click on `Gradle Settings`.
  From there, check the Gradle JDK dropdown select box at the bottom
  and select the option to use `JAVA_HOME`.
- Note: this setting is persisted only for some time. Android Studio
  seems to occasionally reset this to the default `jbr-17` selection.
  At least invalidating all caches does this, but it also seems to
  restart whenever I've had a Gradle build error and tried to restart
  Android Studio.

TODO: Figure out how to properly persist this selection to Android
Studio!

## Gradle corrupted

Sometimes, when making changes to project dependencies and trying
to clean and sync the project, Gradle gets totally corrupted and
refuses to run any script, even from a working project configuration.

Symptoms:

- Check out a working version of project with no changes to Gradle
  setup.
- Try to run any script (even `./gradlew clean`).
- Gradle refuses to run, throwing errors unrelated to your project,
  possibly referring to basic Kotlin classes.

Solution:

- You need to clear out Gradle's caches. They are probably located
  somewhere under `~/.gradle` or similar.
- Sometimes just deleting `~/.gradle/caches` resolves the issue.
- Sometimes I had to delete the entire `~/.gradle` directory
  (this also removes the downloaded Gradle distributions).
