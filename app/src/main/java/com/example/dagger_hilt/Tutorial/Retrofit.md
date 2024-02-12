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

## Repository

El código proporcionado es una implementación de un repositorio de películas en Java, que utiliza el
patrón de diseño Singleton. El repositorio interactúa con una base de datos local (a través de un
objeto `MovieDao`), un servicio remoto para obtener datos de películas (a través de un
objeto `MovieService`), y un conjunto de ejecutores de aplicación (a través de un
objeto `AppExecutors`).

### Clase MovieRepository

La clase `MovieRepository` implementa la interfaz `IMovieRepository` y actúa como una capa
intermedia entre la fuente de datos (servicio remoto y base de datos local) y la capa de
presentación de la aplicación.

#### Atributos

- `dao`: Instancia de `MovieDao` que proporciona métodos para interactuar con la base de datos
  local.
- `service`: Instancia de `MovieService` que proporciona métodos para realizar solicitudes al
  servicio remoto.
- `appExecutors`: Instancia de `AppExecutors`, que se utiliza para ejecutar operaciones en
  subprocesos diferentes al principal de la aplicación.

#### Constructor

El constructor de `MovieRepository` recibe instancias de `MovieDao`, `MovieService`
y `AppExecutors`, que luego se asignan a los atributos correspondientes de la clase.

#### Métodos

- `loadMovies()`: Este método carga las películas. Utiliza una clase interna anónima que hereda
  de `NetworkBoundResource` para gestionar la obtención de datos de la red o de la base de datos
  local. Si los datos en la base de datos local están vacíos, se realiza una llamada a la red para
  obtener los datos. Una vez que se obtienen los datos de la red, se guardan en la base de datos
  local y se devuelven a través de un objeto LiveData.

- `updateMovie(int id, Boolean check)`: Este método actualiza una película en la base de datos
  local. Recibe un identificador de película y un booleano que indica si la película está marcada o
  no.

#### Patrón Singleton

La anotación `@Singleton` aplicada a la clase `MovieRepository` indica que esta clase debe tener una
sola instancia en toda la aplicación. Esto garantiza que todas las partes de la aplicación compartan
la misma instancia de `MovieRepository`.

### Conclusión

En resumen, `MovieRepository` es una clase que maneja la recuperación y el almacenamiento de datos
de películas, interactuando tanto con una base de datos local como con un servicio remoto, y
proporciona métodos para cargar películas y actualizar su estado. Esta clase sigue el principio de
responsabilidad única al separar las preocupaciones de la lógica de negocio y la fuente de datos.

### Java

```java

@Singleton
public class MovieRepository implements IMovieRepository {
    private final MovieDao dao;
    private final MovieService service;
    private final AppExecutors appExecutors;

    @Inject
    public MovieRepository(MovieDao dao, MovieService service, AppExecutors appExecutors) {
        this.dao = dao;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> loadMovies() {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MoviesResponse moviesResponse) {
                List<MovieEntity> moviesList = new ArrayList<>();
                for (MovieModel movie : moviesResponse.getListMovies()) {
                    moviesList.add(new MovieEntity(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getOriginalTitle(),
                            movie.getOverview(),
                            movie.getPosterPath(),
                            false
                    ));
                }
                dao.setMovies(moviesList);
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return dao.getMovies();
            }

            @Override
            protected LiveData<ApiResponse> createCall() {
                return service.loadMovies(Constants.API_KEY);
            }
        }.asLiveData();
    }

    @Override
    public void updateMovie(int id, Boolean check) {
        dao.updateMovie(id, check);
    }
}

```

### Kotlin

```kotlin
@Singleton
class MovieRepositoryKts @Inject constructor(
    private val dao: MovieDaoKts,
    private val service: MovieServiceKts,
    private val appExecutor: AppExecutorsKts
) : IMovieRepositoryKts {
    override fun loadMovies(): LiveData<ResourceKts<List<MovieEntityKts>>> =
        object : NetworkBoundResourceKts<List<MovieEntityKts>, MoviesResponseKts>(appExecutor) {
            override fun saveCallResult(response: MoviesResponseKts) =
                response.listMovies.forEach { movie ->
                    dao.setMovie(
                        MovieEntityKts(
                            movie.id,
                            movie.title,
                            movie.originalTitle,
                            movie.overview,
                            movie.posterPath,
                            like = false
                        )
                    )
                }

            override fun shouldFetch(data: List<MovieEntityKts>?): Boolean = data.isNullOrEmpty()

            override fun loadFromDb(): LiveData<List<MovieEntityKts>> = dao.getMovies()

            override fun createCall(): LiveData<ApiResponseKts<MoviesResponseKts>> =
                service.loadMovies(ConstantsKts.API_KEY)
        }.asLiveData()

    override fun updateMovie(id: Int, check: Boolean) = dao.updateMovie(id, check)

}
```

## NetworkBoundResource

El código proporcionado es una implementación de la clase abstracta `NetworkBoundResource` que
gestiona la carga de datos desde una fuente de red y una fuente de base de datos local. Esta clase
utiliza LiveData de Android Architecture Components para propagar los resultados y eventos
relacionados con la carga de datos.

## Descripción de la clase `NetworkBoundResource`

La clase `NetworkBoundResource` es parametrizada por dos tipos genéricos `ResultType`
y `RequestType`. `ResultType` representa el tipo de datos que se devuelve al cliente, mientras
que `RequestType` representa el tipo de datos que se obtiene de la red.

### Atributos

- `appExecutors`: Un objeto de la clase `AppExecutors` que proporciona subprocesos para ejecutar
  operaciones de red y de base de datos.
- `result`: Un objeto `MediatorLiveData` que almacena y emite los resultados de la carga de datos.

### Constructor

- El constructor toma un objeto `AppExecutors` como argumento y configura inicialmente el estado de
  la carga de datos como "cargando" (`Resource.loading`).
- Llama al método `loadFromDb()` para obtener los datos de la base de datos local.
- Dependiendo del resultado de la carga de datos de la base de datos local y la lógica definida
  en `shouldFetch(data)`, decide si se deben obtener los datos de la red o no.

### Métodos

- `fetchFromNetwork(LiveData<ResultType> dbSource)`: Método privado que se encarga de obtener los
  datos de la red y manejar las respuestas de la API.
- `asLiveData()`: Método público que devuelve el LiveData que contiene los resultados de la carga de
  datos.
- `setValue(Resource<ResultType> newValue)`: Método privado para establecer el valor del LiveData
  resultante.
- `onFetchFailed()`: Método protegido que se llama cuando falla la carga de datos desde la red.
- `saveCallResult(RequestType requestType)`: Método abstracto que debe ser implementado para guardar
  los datos obtenidos de la red en la base de datos local.
- `processResponse(ApiSuccessResponse<RequestType> response)`: Método abstracto que procesa la
  respuesta exitosa de la API y devuelve los datos relevantes.
- `createCall()`: Método abstracto que debe ser implementado para realizar la llamada a la API y
  obtener los datos de la red.
- `shouldFetch(ResultType data)`: Método abstracto que determina si se deben obtener los datos de la
  red basándose en el estado de los datos locales.

La clase `NetworkBoundResource` proporciona una estructura general para implementar el patrón de
repositorio en aplicaciones de Android, separando claramente la lógica de recuperación de datos de
la lógica de presentación y proporcionando una gestión coherente de la carga de datos desde la red y
la base de datos local.