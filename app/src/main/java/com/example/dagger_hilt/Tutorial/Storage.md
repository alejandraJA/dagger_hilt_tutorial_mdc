# Storage

Para el storage, se creará una clase llamada Storage en la ruta: (Paquete)>data>
datasourse>[storage](../data/datasource/storage), y la cual recibirá una instancia de SharedPreferences por medio de
inyección de dependencias, anteponiendo la anotación `@Inject` en el constructor. Esta clase contendrá los métodos de
lectura y escritura por tipo de datos. En el ejemplo siguiente solo se presenta para datos de tipo String.

## Kotlin
```kotlin
class Storage @Inject constructor(
@ApplicationContext private val app: Context
) {

    private val sharedPreferences = 
        app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE) 
 
    fun setString(key: String, value: String) { 
        with(sharedPreferences.edit()) { 
            putString(key, value) 
            apply() 
        } 
    } 
 
    fun getString(key: String): String { 
        return sharedPreferences.getString(key, "")!! 
    } 

}
```
- Consulte [StorageKts](../data/datasource/storage/StorageKts.kt) para visualizarlo mejor.
- Consulte [StorageRepositoryKts](../domain/StorageRepositoryKts.kt) para ver su implementación.

## Java
```java
public class Storage {

    @Inject
    @ApplicationContext
    Context app;

    @Inject
    public Storage() {
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor =
                app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return
                app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE).getString(key, "");
    }
}
```
- Consulte [Storage](../data/datasource/storage/Storage.java) para visualizarlo mejor.
- Consulte [StorageRepository](../domain/StorageRepository.java) para ver su implementación.