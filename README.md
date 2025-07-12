# Teya take home assignment

Take home assignment for Teya's Mobile Engineer interview.

## Running

To run the app simply open it on AndroidStudio and run the app module. The project was tested with
Android Studio Narwhal | 2025.1.1.

## Unit tests

List of Unit Tests:

* `FeedResponseSerializerTest` showcasing a simple serializer. Also I used TDD for this one.
* `AlbumDetailsViewModelTest` showcasing a ViewModel test with Flow/Coroutines.
* `IntegrationTest` showcasing an integration test using Robolectric, Compose test rule, Activity
  scenario rule, Hilt and ktor's mock engine. The mocked classes are in the `TestModule` (just the minimum amount mocked).

Run the command from the root directory:

```
$ ./gradlew testDebugUnitTest
```

# Things to improve

* UX could be polished
* UI is not responsive. Would break in landscape or on bigger device.