# Implementación de Dagger Hilt en Android con Arquitectura ligera

## Tecnologías Utilizadas

- **[Dagger](https://dagger.dev/dev-guide/android.html)**
- **[Gson](https://github.com/google/gson)**
- **[Retrofit](https://square.github.io/retrofit/)**
- **[Glide](https://github.com/bumptech/glide)**
- **[Room](https://developer.android.com/jetpack/androidx/releases/room)**
- **Android Lifecycle**

---

## Implementación

- Configuración de Gradle.
    - [Build Config](app/src/main/java/com/example/dagger_hilt/Tutorial/BuildConfig.md).
    - [Dependencias](app/src/main/java/com/example/dagger_hilt/Tutorial/Dependencies.md).
- [Estructura de los paquetes](app/src/main/java/com/example/dagger_hilt/Tutorial/pakage.md).
- [Vinculación de vistas viewBinding y dataBinding](app/src/main/java/com/example/dagger_hilt/Tutorial/Binding.md).
- Capa de Datos.
    - [Room](app/src/main/java/com/example/dagger_hilt/Tutorial/Room.md).
    - [Retrofit](app/src/main/java/com/example/dagger_hilt/Tutorial/Retrofit.md).
    - [Storage](app/src/main/java/com/example/dagger_hilt/Tutorial/Storage.md).
- [Inicio de Implementación de inyección de dependencias con Dagger-Hilt](app/src/main/java/com/example/dagger_hilt/Tutorial/DependenciesInjection.md).
  - [Modulos](app/src/main/java/com/example/dagger_hilt/Tutorial/Modulos.md) (Explicación).
  - [Implementación](app/src/main/java/com/example/dagger_hilt/Tutorial/Implementacion.md).
- Capa de Dominio (Repositorios y UseCase). 
  - [Teoría](app/src/main/java/com/example/dagger_hilt/Tutorial/Repository.md)
  - Ejemplos
    - [DatabseRepository](app/src/main/java/com/example/dagger_hilt/domain/DatabaseRepository.kt)
    - [ApiRepository](app/src/main/java/com/example/dagger_hilt/domain/ApiRepository.kt)
    - [StorageRepository](app/src/main/java/com/example/dagger_hilt/domain/StorageRepository.kt)
- Capa de Vistas
  - [ViewModel](app/src/main/java/com/example/dagger_hilt/Tutorial/ViewModel.md)
  - [Views](app/build/generated/res/resValues/debug/com/example/dagger_hilt/Tutorial/Views.md)