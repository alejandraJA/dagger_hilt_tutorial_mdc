# Proyecto de Ejemplo: Aplicación Android con Clean Architecture

Este proyecto de ejemplo se centra en la creación de una aplicación Android utilizando las mejores
prácticas recomendadas por la arquitectura de Android. Implementa Clean Architecture para garantizar
una separación clara de responsabilidades y promover la escalabilidad del código.

## Tecnologías Utilizadas:

- **[Dagger](https://dagger.dev/dev-guide/android.html)** Lenguajes de programación principales para
  el desarrollo de la aplicación.
- **[Gson](https://github.com/google/gson)** Biblioteca utilizada para la inyección de dependencias.
- **[Retrofit](https://square.github.io/retrofit/)** Utilizado para realizar llamadas a APIs REST.
- **[Glide](https://github.com/bumptech/glide)**  Para la gestión de la persistencia de datos local.
- **[Room](https://developer.android.com/jetpack/androidx/releases/room)** Gestión del ciclo de vida
  de los componentes de la aplicación.
- **Android Lifecycle** Librería para la carga eficiente de imágenes.

## Objetivo del Proyecto:

Este proyecto se ha creado con la intención de proporcionar un ejemplo claro y didáctico para
aquellos interesados en aprender a utilizar estas tecnologías en sus propios proyectos Android. La
documentación y el código incluyen ejemplos prácticos y comentarios detallados para facilitar la
comprensión del uso de estas herramientas en conjunto, siguiendo las recomendaciones de la
arquitectura Android.

## Uso del Proyecto:

El código y la estructura del proyecto están organizados de manera que sea fácil de entender y
seguir. Este material está diseñado para servir como recurso de aprendizaje para desarrolladores que
deseen profundizar en el desarrollo de aplicaciones móviles para Android, utilizando herramientas
modernas y siguiendo estándares de calidad.

¡Explora, experimenta y aprende con este proyecto! Esperamos que encuentres esta documentación y el
código adjunto útiles para tu desarrollo como programador de aplicaciones móviles en la plataforma
Android.
---

# Implementación de Dagger Hilt en Android con Arquitectura ligera

## Implementación

- Configuración de Gradle.
    - [Build Config](Tutorial/BuildConfig.md).
    - [Dependencias](Tutorial/Dependencies.md).
- [Estructura de los paquetes](Tutorial/pakage.md).
- [Vinculación de vistas viewBinding y dataBinding](Tutorial/Binding.md).
- Capa de Datos.
    - [Room](Tutorial/Room.md).
    - [Retrofit](Tutorial/Retrofit.md).
    - [Storage](Tutorial/Storage.md).
- [Inicio de Implementación de inyección de dependencias con Dagger-Hilt](Tutorial/DependenciesInjection.md).
    - [Modulos](Tutorial/Modulos.md) (Explicación).
    - [Implementación](Tutorial/Implementacion.md).
- Capa de Dominio (Repositorios y UseCase).
    - [Teoría](Tutorial/Repository.md)
    - Ejemplos
        - [DatabaseRepository](domain/DatabaseRepositoryKts.kt)
        - [ApiRepository](domain/ApiRepositoryKts.kt)
        - [StorageRepository](domain/StorageRepositoryKts.kt)
- Capa de Vistas
    - [ViewModel](Tutorial/ViewModel.md)
    - [Views](Tutorial/Views.md)
- [Correr el proyecto](Tutorial/Run.md)