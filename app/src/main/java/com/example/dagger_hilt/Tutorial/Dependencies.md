# Dependencias del proyecto

## Descripción general

El archivo `build.gradle` a nivel de proyecto establece los plugins esenciales necesarios para la
configuración del proyecto. Aquí, se están configurando herramientas y bibliotecas fundamentales que
serán utilizadas a lo largo del desarrollo de la app.

Los plugins listados son:

- `com.android.application`: Este plugin es esencial para aplicaciones Android, estableciendo las
  configuraciones principales para la compilación de la app.
- `org.jetbrains.kotlin.android`: Plugin de Kotlin para Android, que permite escribir código en
  Kotlin y su interoperabilidad con Java en aplicaciones Android.
- `com.google.devtools.ksp`: KeyPoint Stub (KSP) es un compilador de Kotlin usado principalmente
  para generar código de procesamiento durante el tiempo de compilación.
- `com.google.dagger.hilt.android`: Este plugin específico para Dagger Hilt es crucial para la
  inyección de dependencias en Android, simplificando este proceso dentro de la aplicación.

Dentro del archivo de configuración específico de la aplicación (`app/build.gradle`), se definen más
plugins y dependencias específicas de la app:

Los plugins son:

- `org.jetbrains.kotlin.android`: Ya mencionado en el archivo a nivel de proyecto, se aplica aquí
  también para configurar Kotlin en la app.
- `kotlin-kapt`: Este plugin habilita la compilación de anotaciones Kotlin para bibliotecas como
  Dagger y Hilt, así como para el uso de Data Binding.
- `com.google.devtools.ksp`: Igualmente, se vuelve a mencionar para su uso en la configuración
  específica de la aplicación.
- `dagger.hilt.android.plugin`: Plugin específico para Dagger Hilt en Android, permitiendo la
  integración de la inyección de dependencias en la app.

Las dependencias listadas en la sección `dependencies` son bibliotecas y herramientas que la
aplicación utilizará:

- `Hilt` y `Dagger`: Dependencias necesarias para la inyección de dependencias.
- `Room`: Biblioteca de persistencia que simplifica trabajar con bases de datos en Android.
- `Glide` y `Picasso`: Bibliotecas para cargar y mostrar imágenes en la aplicación.
- `Gson`: Utilizada para serializar y deserializar objetos Java en JSON.
- `Retrofit`: Herramienta para consumir servicios web y API REST.
- `Lifecycle`: Proporciona componentes de arquitectura de Android para ayudar a crear apps robustas
  y mantenibles.
- `Interceptor`: Herramientas para la manipulación de peticiones HTTP, en este caso, se usa con
  OkHttp para logging e intercepción de llamadas de red.

En conjunto, esta configuración establece una base sólida para el desarrollo de una app Android,
integrando herramientas esenciales como Kotlin, Dagger Hilt para inyección de dependencias,
bibliotecas para manejo de imágenes, persistencia en bases de datos, consumo de servicios web y
gestión del ciclo de vida de la app.

## Implementación

El el archivo [build.gradle](../../../../../../../build.gradle) (en caso de usar Groovy DSL) o
[build.gradle.kts](../../../../../../../build.gradle.kts) (en case de usar con Kotlin DSL) se
agregarán los plugin.

- Kotlin
- Dagger-Hilt
- KSP

### Groovy DSL

```groovy
plugins {
    id 'com.android.application' version '8.2.0' apply false
    id 'com.android.library' version '8.2.0' apply false

    id 'org.jetbrains.kotlin.android' version '1.9.10' apply false
    id 'com.google.devtools.ksp' version '1.9.10-1.0.13' apply false
    id 'com.google.dagger.hilt.android' version '2.49' apply false
}
```

### Kotlin DSL

```kotlin
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
}
```

En el archivo de [app/build.gradle](../../../../../../../build.gradle) (en caso de usar Groovy DSL)
o [app/build.gradle.kts](../../../../../../../build.gradle.kts) (en case de usar con Kotlin DSL) se
agregaran los siguientes
plugin

- Kotlin.
- Kapt (En caso de usar Hilt y Dagger con Kapt, además de emplearlo en el uso de dataBinding).
- Ksp.
- Hilt.

### Groovy DSL

```groovy
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'com.google.devtools.ksp'
    id 'dagger.hilt.android.plugin'
}
```

### Kotlin DSL

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
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

### Gradle DSL

```groovy
dependencies {
    // ...
    // Other Dependencies
    // ...

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

    // timber
    implementation 'com.jakewharton.timber:timber:4.7.1'

    //interceptor
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
    implementation 'com.squareup.okhttp3:okhttp-urlconnection:4.3.0' // Java Net Cookie Jar
}
```

### Kotlin DSL

```kotlin
dependencies {
    // ...
    // Otras dependencias
    // ...

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.49")

    // Dagger
    implementation("com.google.dagger:dagger:2.49")
    ksp("com.google.dagger:dagger-compiler:2.49")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    ksp("com.github.bumptech.glide:ksp:4.14.2")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // GSON
    implementation("com.google.code.gson:gson:2.10")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.3.0") // Java Net Cookie Jar
}
```