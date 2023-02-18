# Sarimsakov_Fintech2023

Основные требования к приложению:
1. На главном экране необходимо отображать список популярных фильмов.
## (1) Выпольнено
2. В каждой карточке фильма на главной странице должны содержаться следующие элементы:
2.1 Наименование фильма.
2.2 Изображение-постер фильма.
2.3 Год выпуска.
## (2) Выпольнено
3. При клике на карточку открывается экран с постером фильма, описанием, жанром, страной
производства.
## (3) Выпольнено
4. Если сеть недоступна или в процессе загрузки произошла ошибка, необходимо предусмотреть
уведомление пользователя об этом.
## (4) Выпольнено

Необязательные требования, но будет плюсом, если Вы сможете их реализовать:
1. При смене ориентации устройства, список фильмов занимает только 50% экрана, во второй
половине будет отображаться описание фильма.
## (1) НЕ Выпольнено
2. На главном экране присутствуют разделы «Популярное» и «Избранное». При длительном клике на
карточку, фильм помещается в избранное и хранится в базе данных. Карточки фильмов из
избранного доступны в оффлайн-режиме.
## (2) Выпольнено
3. При просмотре популярных, выделяются фильмы, находящиеся в избранном.
## (3) Выпольнено
4. В разделах доступен поиск фильмов по наименованию (в соответствии с выбранным разделом).
## (4) Выпольнено

Так же будет здорово, если:
• Приложение написано на Kotlin.
## Выпольнено
• Обеспечена общая плавность и стабильность приложения.
## Выпольнено
• Во время длительных загрузок, отображаются шиммеры/прогресс бары.
## Выпольнено
• Ответы от API должны быть закешированы хотя бы на время сессии.
## Выпольнено
• Приложение покрыто UNIT тестами.

## Architecture

The architecture of the application is based, apply and strictly complies with each of the following 5 points:
-   A single-activity architecture!
-   [Android architecture components](https://developer.android.com/topic/libraries/architecture/), part of Android Jetpack for give to project a robust design, testable and maintainable.
-   Pattern  [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)  (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface.
-   [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID)  design principles intended to make software designs more understandable, flexible and maintainable.

## Tech-stack
Min API level is set to 21, so the presented approach is suitable for over 94% of devices running Android. This project takes advantage of many popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version unless there is a good reason to use non-stable dependency.
-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html)  - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx)  - major improvement to the original Android  [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [View Binding](https://developer.android.com/topic/libraries/view-binding)  - allows you to more easily write code that interacts with views/
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)  - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room)  - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - managing background threads with simplified code and reducing needs for callbacks.
-    [Coroutines Flow](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - cold asynchronous data stream that sequentially emits values and completes normally or with an exception
-   [Koin](https://insert-koin.io/)  - dependency injector for replacement all Factory classes.
-   [Retrofit](https://square.github.io/retrofit/)  - type-safe HTTP client.

## Authors

**Sarimsakov Sardor**

[![Linkedin]](https://www.linkedin.com/in/sardor-sarimsakov-b13b17173/)
