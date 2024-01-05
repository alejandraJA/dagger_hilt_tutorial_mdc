# Retrofit

## Modelos

En la arquitectura de una API, los modelos son representaciones estructuradas de los datos
utilizados tanto para enviar solicitudes como para recibir respuestas. En el contexto de Retrofit,
estos modelos se definen en un directorio específico de la aplicación.

### Kotlin

En Kotlin, la definición de modelos se simplifica mediante el uso de clases de datos. Aquí está un
ejemplo de cómo se puede definir un modelo de película:

```kotlin
data class MovieModel(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("original_title")
    var originalTitle: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("poster_path")
    var posterPath: String,
)
```

Puedes encontrar una representación más detallada de este modelo
en [MovieModelKts](../data/datasource/web/models/MovieModelKts.kt).

### Java

En Java, la definición de modelos implica la creación de clases con miembros correspondientes a los
datos que se esperan de la API. Aquí está un ejemplo de un modelo de película en Java:

```java
public class MovieModel {
    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("overview")
    String overview;

    @SerializedName("poster_path")
    String posterPath;

    // Getters y Setters
}
```

Puedes observar más detalles sobre este modelo
en [MovieModel](../data/datasource/web/models/MovieModel.java).

## API

### Kotlin

Dentro del contexto de Retrofit, el servicio se define como una interfaz que declara métodos para
interactuar con los puntos finales de la API.

#### Service

En Kotlin, un ejemplo de un servicio que utiliza Retrofit para cargar películas sería:

```kotlin
interface MovieServiceKts {
    @GET("discover/movie")
    fun loadMovies(
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponseKts<MoviesResponseKts>>
}
```

Para una visualización más detallada de este servicio,
revisa [MovieServiceKts](../data/datasource/web/api/MovieServiceKts.kt).

### Java

En Java, la definición del servicio implica la creación de una interfaz que define los métodos de la
API utilizando anotaciones de Retrofit.

#### Service

Aquí tienes un ejemplo de cómo se vería un servicio para cargar películas en Java:

```java
public interface MovieService {
    @GET("discover/movie")
    LiveData<ApiResponse> loadMovies(@Query("api_key") String apiKey);
}
```

Puedes echar un vistazo más cercano a esta implementación
en [MovieService](../data/datasource/web/api/MovieService.java).

Esta estructura modular y bien definida facilita la comunicación con la API a través de Retrofit en
entornos tanto de Kotlin como de Java.