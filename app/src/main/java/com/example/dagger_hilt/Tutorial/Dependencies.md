# Dependencias del proyecto

El el archivo [build.gradle](../../../../../../../../build.gradle) se agregarán los siguientes
plugin.

- Kotlin
- Dagger-Hilt
- KSP

```gradle
plugins {
    id 'com.android.application' version '8.2.0' apply false
    id 'com.android.library' version '8.2.0' apply false

    id 'org.jetbrains.kotlin.android' version '1.9.10' apply false
    id 'com.google.devtools.ksp' version '1.9.10-1.0.13' apply false
    id 'com.google.dagger.hilt.android' version '2.49' apply false
}
```

En el archivo de [app/build.gradle](../../../../../../../build.gradle) se agregaran los siguientes
plugin

- Kotlin
- Kapt (En caso de usar Hilt y Dagger con Kapt)
- Ksp
- Hilt

```gradle
plugins {
    id 'com.android.application'

    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'com.google.devtools.ksp'
    id 'dagger.hilt.android.plugin'
}
```

En el mismo archivo, pero dentro de la sección de `android/dependencies`, se colocarán las
siguientes dependenias

- Hilt
- Dagger
- Room
- Glide
- Gson
- Retrofit
- Lifecycle
- Interceptor

```gradle
dependencies {
  ...
  // Other Dependencies
  ...

  // Hilt
  implementation "com.google.dagger:hilt-android:2.49"
  ksp "com.google.dagger:hilt-android-compiler:2.49"

  // Dagger
  implementation 'com.google.dagger:dagger:2.49'
  ksp 'com.google.dagger:dagger-compiler:2.49'

  // Room
  implementation "androidx.room:room-runtime:2.6.1"
  ksp "androidx.room:room-compiler:2.6.1"

  // GLIDE
  implementation 'com.github.bumptech.glide:glide:4.14.2'
  ksp 'com.github.bumptech.glide:ksp:4.14.2'

  // Picasso
  implementation "com.squareup.picasso:picasso:2.71828"

  // GSON
  implementation 'com.google.code.gson:gson:2.10'

  // RETROFIT
  implementation "com.squareup.retrofit2:retrofit:2.9.0"
  implementation "com.squareup.retrofit2:converter-gson:2.9.0"

  //LifeCycle
  implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
  implementation 'androidx.legacy:legacy-support-v4:1.0.0'
  implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
  implementation "androidx.fragment:fragment-ktx:1.6.2"
  implementation "androidx.activity:activity-ktx:1.8.2"
  implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'

  //interceptor
  implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
  implementation 'com.squareup.okhttp3:okhttp:4.9.0'
  implementation 'com.squareup.okhttp3:okhttp-urlconnection:4.3.0'
}
```