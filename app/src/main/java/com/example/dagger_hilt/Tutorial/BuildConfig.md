# BuildConfig

## Descripción general

Este código se encuentra en un archivo Gradle específico para configurar la construcción de una app
Android. Dentro de él, se definen dos variantes de construcción: una para pruebas (debug) y otra
para producción (release). Cada una de estas variantes tiene configuraciones específicas que se
aplican al momento de compilar la app.

En la sección de "Variable de Pruebas", se definen ciertas características que son útiles durante el
desarrollo y pruebas de la aplicación:

- `applicationIdSuffix ".debug"`: Agrega un sufijo al ID del paquete de la aplicación en modo de
  depuración, lo que puede ser útil para distinguir entre la versión de producción y la de prueba.
- `debuggable true`: Permite que la aplicación sea depurada y se conecte con herramientas de
  depuración durante el desarrollo.
- `versionNameSuffix "-alpha"`: Agrega un sufijo al nombre de la versión para identificar que es una
  versión en fase alfa.
- Las líneas que comienzan con `buildConfigField` definen campos en la clase `BuildConfig` de la
  aplicación que pueden ser accedidos desde el código fuente de la app. En este caso, se están
  definiendo las URLs de la API y la clave de la API de "The Movie Database" para la versión de
  pruebas.

En cuanto a la sección de "Variable de Producción":

- `minifyEnabled false`: Desactiva la optimización y ofuscación del código, lo que facilita la
  depuración en caso de errores en la versión de producción.
- `proguardFiles ...`: Configura archivos específicos para el proceso de ofuscación del código en la
  versión de producción.
- Al igual que en la sección de pruebas, se definen las mismas URLs y clave de API, pero esta vez
  para la versión de producción.

Este código Gradle es crucial para establecer configuraciones específicas que diferencian la versión
de desarrollo de la versión de producción de una app Android. Facilita el manejo de diferentes
variables (como URLs de servidor, claves de API) para asegurar que la app utilice diferentes
configuraciones según el entorno en el que se esté ejecutando.

## Implementación

En el archivo [app/build.gradle](../../../../../../../build.gradle) (en caso de usar Groovy DSL) o
[app/build.gradle.kts](../../../../../../../build.gradle.kts) (en case de usar con Kotlin DSL) se
agragarán las siguientes variables, dentro del apartado de buildTypes.

```groovy
plugins {
}

android {

    buildTypes {
        variable1 {

        }

        variable2 {

        }
    }
}
```

## Variable de Pruebas

### Groovy DSL

```groovy
debug {
    applicationIdSuffix ".debug"
    debuggable true
    versionNameSuffix "-alpha"
    buildConfigField "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
    buildConfigField "String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\""
    buildConfigField "String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\""
}
```

### Kotlin DSL
```kotlin
debug {
  applicationIdSuffix(".debug")
  isDebuggable = true
  versionNameSuffix("-alpha")
  buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
  buildConfigField("String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\"")
  buildConfigField("String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\"")
}
```

## Variable de Producción

### Groovy DSL

```groovy
release {
    minifyEnabled false
    proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    buildConfigField "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
    buildConfigField "String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\""
    buildConfigField "String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\""
}
```

### Kotlin DSL
```kotlin
relese {
  isMinifyEnabled = false
  proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
  buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
  buildConfigField("String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\"")
  buildConfigField("String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\"")
}
```