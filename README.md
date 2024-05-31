<div align="center">
  <a href=""><img width="200" height="200" src="https://github.com/Pool-Of-Tears/Myne/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png"></a>
  <h2>WellSpring: Download & Read eBooks</h2>
</div>

<p align="center">
  <a href="https://www.android.com"><img src="https://forthebadge.com/images/badges/built-for-android.svg"></a>
  <a href="https://www.github.com/starry-shivam"><img src="https://forthebadge.com/images/badges/built-with-love.svg"/></a>
</p>



------

**WellSpring** is a [FOSS](https://en.m.wikipedia.org/wiki/Free_and_open-source_software) Android application for downloading and reading ebooks from [Project Gutenberg](https://gutenberg.org). It uses the [GutenDex](https://github.com/garethbjohnson/gutendex) API to fetch metadata for ebooks in the backend. Additionally, it functions as an EPUB reader, allowing you to easily import and immerse yourself in your favorite EPUB books!




>[!Note]
>
>The app also utilizes the Google Books API to retrieve additional data such as synopsis and page count. As the Gutenberg project does not include these values in their metadata. While the app attempts to map the data received from Google Books with Gutenberg's metadata, the mapping is not always 100% accurate. Additionally, not all books available on Gutenberg are accessible on Google Books, or they may be available under different titles. As a result, you may find some books without synopsis or page count, etc.

------

<h2 align="center">Screenshots</h2>

| ![](https://graph.org/file/a86b19969bbba506fcdfa.png) | ![](https://graph.org/file/7d1964fb46fd1f4ebf98c.png) | ![](https://graph.org/file/14bc72fa57c42deaf03bf.png) |
|-------------------------------------------------------|-------------------------------------------------------|-------------------------------------------------------|
| ![](https://graph.org/file/3b2b55557ef23d96213ba.png) | ![](https://graph.org/file/0278019a5078d6f8a6155.png) | ![](https://graph.org/file/c44d5574399783320be10.png) |

------

<h2 align="center">Highlights</h2>

- Clean & beautiful UI based on Google's [material design three](https://m3.material.io/) guidelines.
- Browse and download over 70k free ebooks available in multiple languages and updated daily.
- Comes with inbuilt ebook reader while also having an option to use third-party ebook readers.
- Compatible with Android 8.0 and above (API 26+)
- Supports [Material You](https://www.androidpolice.com/everything-we-love-about-material-you/amp/) theming in devices running on Android 12+
- Comes in both light and dark mode.
- MAD: UI and logic written with pure Kotlin. Single activity, no fragments, only composable destinations.

------



------



<h2 align="center">Contributions</h2>

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change, or feel free to tackle any of the open issues present at the moment. If you're doing the latter, please leave a comment on the issue you want to contribute towards before starting to work on it.

------

<h2 align="center">Translations</h2>

If you want to make the app available in your language, you're welcome to create a pull request with your translation file. The base string resources can be found under:
```
/app/src/main/res/values/strings.xml
```
It is easiest to make a translation using the Android Studio XML editor, but you can always use your favorite XML text editor instead. Check out this guide to learn more about translation strings from [Helpshift](https://developers.helpshift.com/android/i18n/) for Android.

------

<h2 align="center">Tech Stack</h2>

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - To improve performance by doing I/O tasks out of main thread asynchronously.
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [Jetpack Compose](https://developer.android.com/jetpack/compose?gclsrc=ds&gclsrc=ds) - Jetpack Compose is Android’s recommended modern toolkit for building native UI
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [OkHttp3](https://square.github.io/okhttp/) - OkHttp is an HTTP client for Android that’s efficient by default.
- [Gson](https://github.com/google/gson) - A Java serialization/deserialization library to convert Java Objects into JSON and back.
- [Jsoup](https://jsoup.org) - Jsoup is a Java library for working with HTML. It provides a convenient API for extracting and manipulating data, using the HTML5 DOM methods and CSS selectors.
- [Coil](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines.
- [Dagger-Hilt](https://dagger.dev/hilt/) For [Dependency injection (DI)](https://developer.android.com/training/dependency-injection)
- [Room database](https://developer.android.com/jetpack/androidx/releases/room) - Persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

------

