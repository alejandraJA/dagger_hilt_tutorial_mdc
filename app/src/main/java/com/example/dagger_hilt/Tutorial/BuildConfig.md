# BuildConfig

En el archivo [app/build.gradle](../../../../../../../build.gradle) se agragarán las siguientes
variables, dentro del
apartado de buildTypes.

```gradle
plugins {
    ...
}

android {
    ...
    ...
    buildTypes {
        variable1 {
            ...
        }

        variable2 {
            ...
        }
    }
}
```

## Variable de Pruebas

```gradle
debug {
    applicationIdSuffix ".debug"
    debuggable true
    versionNameSuffix "-alpha"
    buildConfigField "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
    buildConfigField "String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\""
    buildConfigField "String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\""
}
```

## Variable de Producción

```gradle
release {
    minifyEnabled false
    proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    buildConfigField "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
    buildConfigField "String", "API_KEY", "\"f1d25ec5c0f90889e99aff4ab6a492ad\""
    buildConfigField "String", "BASE_URL_IMAGES", "\"https://image.tmdb.org/t/p/w500\""
}
```