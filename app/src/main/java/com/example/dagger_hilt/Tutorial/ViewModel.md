# ViewModel

Todos los ViewModel que reciban instancias por inyección de dependencias deben tener la anotación:
`@HiltViewModel` y deberán recibir al menos una dependencia, de lo contrario, marcará un error en
tiempo de compilación. A su vez, estos deberán extender de ViewModel.

En el siguiente ejemplo, obtiene el repositorio de la base de datos y realiza operaciones de lectura
y escritura a la base de datos.

## Kotlin

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    fun updateMovie(id: Int, check: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.updateMovie(id, check)
            }
        }
    }

    val movies = MediatorLiveData<List<MovieEntity>>().apply {
        addSource(databaseRepository.movies) {
            if (it.isNotEmpty()) value = it
        }
    }
}
```

Vea [MainViewModel](../ui/activity/main/viewModel/MainViewModelKts.kt) para visualizarlo mejor

## Java

```java

@HiltViewModel
public class MainViewModel extends ViewModel {

    @Inject
    DatabaseRepository databaseRepository;

    @Inject
    public MainViewModel() {
    }

    public void updateMovie(int id, boolean check) {
        databaseRepository.updateMovie(id, check);
    }

    public MediatorLiveData<List<MovieEntity>> getMovies() {
        var movies = new MediatorLiveData<List<MovieEntity>>();
        movies.addSource(databaseRepository.getMovies(), movies::postValue);
        return movies;
    }
}
```

Una de las buenas prácticas para el ViewModel es que, toda la información a la que valla acceder la
vista debe ser encapsulada en un objeto que contenga todos los datos requeridos por la vista, como
miembros de sí mismo.

Por ejemplo, si además de las películas, la vista necesitará la lista de los escritores, deberían
estar comprendidos en un único objeto que tenga como miembros una lista de películas y una de
autores. De este modo, la vista solo tendrá un solo Observer activo. 