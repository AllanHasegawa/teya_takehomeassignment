# Teya take home assignment

Take home assignment for Teya's Mobile Engineer interview.

## Running

To run the app simply open it on AndroidStudio and run the app module. The project was tested with Android Studio Narwhal | 2025.1.1.

## Unit tests

List of Unit Tests:

* `FeedResponseSerializerTest` showcasing a simple serializer. Also I used TDD for this one.

Run the command from the root directory:

```
$ ./gradlew testDebugUnitTest
```

## Instrumentation tests

Run the command from the root directory:

```
$ ./gradlew connectedAndroidTest
```

Ps.: Make sure you have **one** device running and connected to ADB.

# Things to improve

* UX could be polished
* UI is not responsive. Would break in landscape or on bigger device.