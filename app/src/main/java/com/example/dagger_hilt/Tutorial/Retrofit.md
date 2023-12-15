# Retrofit

## Modelos

Cada uno de los modelados usados por el API (tanto para peticiones y respuestas) deberán guardarse
en el directorio: (Paquete)>data>datasourse>web>[models](../data/datasource/web). Y los miembros de
estos objetos llevarán la anotación: `@SerializedName("name")`, dicha etiqueta contendrá el nombre
que tenga el dato en el Json. De igual modo que con la entidad, si no es colocado se toma el nombre
con el que fue nombrada la propiedad del objeto.

### Kotlin

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

Consulte [MovieModel](../data/datasource/web/models/MovieModel.kt) para visualizarlo mejor.

### Java

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
```

## Api

### Kotlin

#### Service

El servicio es una interfaz con los métodos que llaman a los servicios por medio de Retrofit, como
en el siguiente ejemplo:

Los siguientes archivos serán guardados en la ruta: (Paquete)>data>datasourse>
web>[api](../data/datasource/web/api).

```kotlin
interface MovieService {
    @GET("discover/movie")
    suspend fun loadMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>
}
```

Consulte [MovieService](../data/datasource/web/api/MovieService.kt) para visualizarlo mejor.

#### Helper

Es la interfaz que se implementará poco después, en el implementador.

```kotlin
interface MovieHelper {
    suspend fun loadMovies(apiKey: String, webStatus: WebStatus<MoviesResponse>)
}
```

Consulte [MovieHelper](../data/datasource/web/api/MovieHelper.kt) para visualizarlo mejor.

#### Helper Implement

La clase implementadora del helper recibirá una instancia de la interfaz del servicio (por medio de
inyección de dependencias, anteponiendo la anotación `@Inject` en el constructor), para que los
métodos heredados de la interfaz retornen los del servicio.

```kotlin
class MovieHelperImp @Inject constructor(private val movieService: MovieService) : MovieHelper {
    override suspend fun loadMovies(apiKey: String, webStatus: WebStatus<MoviesResponse>) =
        Resolve(movieService.loadMovies(apiKey), webStatus).invoke()
}
```

- Consulte [MovieHelperImp](../data/datasource/web/api/MovieHelperImp.kt) para visualizarlo mejor.
- Consulte [Resolve](../data/datasource/web/util/Resolve.kt).
- Consulte [WebStatus](../data/datasource/web/util/WebStatus.kt).

### Java

#### Service

```java
public interface MovieService {
    @GET("discover/movie")
    Call<MoviesResponse> loadMovies(@Query("api_key") String apiKey);
} 
```