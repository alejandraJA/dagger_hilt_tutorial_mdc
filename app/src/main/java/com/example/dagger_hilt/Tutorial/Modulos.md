# Modulos

Para la inyección de dependencias es necesario crear módulos, encargados de que se implementen las
dependencias, y para ello se usa la etiqueta `@Module`; mientras que la etiqueta `@IntallIn()` será
la encargada de definir el alcance que de las dependencias agrupadas en dicho modulo.

De este modo, Dagger podrá crear una instancia de esa dependencia que morirá cuando llegue a su
alcance definido.

| **Componente de**Hilt         | **Inyector para**                            |
|-------------------------------|----------------------------------------------|
| **SingletonComponent**        | **Application**                              |
| **ActivityRetainedComponent** | **N/A**                                      |
| **ViewModelComponent**        | **ViewModel**                                |
| **ActivityComponent**         | **Activity**                                 |
| **FragmentComponent**         | **Fragment**                                 |
| **ViewComponent**             | **View**                                     |
| **ViewWithFragmentComponent** | **View** anotada con `@WithFragmentBindings` |
| **ServiceComponent**          | **Service**                                  |

# Ciclos de vida de los componentes

Hilt crea y destruye automáticamente instancias de clases generadas por componentes siguiendo el
ciclo de vida de las clases de Android correspondientes.

| **Componente generado**       | **Creado en**              | **Destruido en**            |
|-------------------------------|----------------------------|-----------------------------|
| **SingletonComponent**        | **Application#onCreate**() | **Se destruyó** Application |
| **ActivityRetainedComponent** | **Activity#onCreate**()    | **Activity#onDestroy**()    |
| **ViewModelComponent**        | **Se creó** ViewModel      | **Se destruyó** ViewModel   |
| **ActivityComponent**         | **Activity#onCreate**()    | **Activity#onDestroy**()    |
| **FragmentComponent**         | **Fragment#onAttach**()    | **Fragment#onDestroy**()    |
| **ViewComponent**             | **View#super**()           | **Se destruyó** View        |
| **ViewWithFragmentComponent** | **View#super**()           | **Se destruyó** View        |
| **ServiceComponent**          | **Service#onCreate**()     | **Service#onDestroy**()     |
