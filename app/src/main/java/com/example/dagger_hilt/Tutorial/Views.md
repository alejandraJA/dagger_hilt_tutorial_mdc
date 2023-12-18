# Views

Por último, tenemos la lógica de las vistas. Y aquí es muy importante aclarar que siempre que el
viewModel o la vista reciba datos por inyección de dependencias, debe llevar la etiqueta
`@AndroidEntryPoint`. De lo contrario (aunque sea el ViewModel el que reciba las instancias),
marcará un error cuando el programa utilice dicha vista.

En la actualidad, la versión de Hilt admite las siguientes clases de Android:

- Application (mediante `@HiltAndroidApp`)
- ViewModel (mediante `@HiltViewModel`)
- Activity
- Fragment
- View
- Service
- BroadcastReceiver

Si anotas una clase de Android con `@AndroidEntryPoint`, también debes anotar las clases de Android
que dependen de ella. Por ejemplo, si anotas un fragmento, también debes anotar todas las
actividades en las que uses ese fragmento.

## Ejemplos

### Kotlin

```kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        val movieList: MutableList<MovieEntity>
        movieList = ArrayList()
        val movieAdapter =
            MovieAdapter(movieList) { id: Int, check: Boolean ->
                viewModel.updateMovie(id, check)
            }
        binding.recyclerMovie.setHasFixedSize(true)
        binding.recyclerMovie.adapter = movieAdapter
        /**
         * En caso de que se trate de un Fragment,
         * this será remplazado por viewLifecycleOwner
         */
        viewModel.movies.observe(this) {
            movieList.clear()
            movieList.addAll(it!!)
            movieAdapter.notifyDataSetChanged()
        }
    }
}
```

Consulte [MainActivity](../ui/activity/main/MainActivityKts.kt) para visualizarlo mejor.

### Java

```java
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @SuppressLint("NotifyDataSetChanged") 
    @Override 
    protected void onCreate(@Nullable Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        var viewModel = new ViewModelProvider(this).get(MainViewModel.class); 
        var binding = ActivityMainBinding.inflate(getLayoutInflater()); 
        setContentView(binding.getRoot()); 
        List<MovieEntity> movieList = new ArrayList<>(); 
        var movieAdapter = new MovieAdapter(movieList, viewModel::updateMovie); 
        binding.recyclerMovie.setHasFixedSize(true); 
        binding.recyclerMovie.setAdapter(movieAdapter); 
        /* 
          En caso de que se trate de un Fragment, 
          this será remplazado por viewLifecycleOwner 
         */ 
        viewModel.getMovies().observe(this, movies -> { 
            movieList.clear(); 
            movieList.addAll(movies); 
            movieAdapter.notifyDataSetChanged(); 
        }); 
    } 
}
```