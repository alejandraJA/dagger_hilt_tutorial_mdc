# Room

> **Nota**: Se recomienda que para los objetos de la base de datos y el web service se mantengan por
> separado. Por ende, Room cuenta con su carpeta de Entitys y Retrofit con su carpeta de Models, esto
> para dejar separado uno de otro.

Para crear las tablas, se especifica en el pojo usando los anotadores de procesadores de Room. La
librería exige que contenga una llave primaria, si no se requiere un identificador, se puede definir
un entero como auto incremental con el anotador de proceso `@PrimaryKey`.

## Entidad

Cada uno de los objetos deberán ser guardados en el directorio (Paquete)>data>datasourse>
database>[entieties](../data/datasource/database/entities).

### Kotlin

```kotlin
@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "original_title") var originalTitle: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "poster_path") var posterPath: String,
    @ColumnInfo(name = "like") var like: Boolean
)
```

Consulte [MovieEntity](../data/datasource/database/entities/MovieEntityKts.kt) para visualizarlo mejor.

### Java

```java

@Entity(tableName = "movie")
public class MovieEntity {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    private final String posterPath;
    @ColumnInfo(name = "like")
    private final Boolean like;

    public MovieEntity(int id, String title, String originalTitle, String overview, String posterPath, Boolean like) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.like = like;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
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

    public Boolean getLike() {
        return like;
    }
}
```

`@ColumnInfo(name="")` sirve para especificar el nombre que llevara la columna en la base de datos,
aunque (de igual
modo), si no es colocada dicha etiqueta, se configurará con el nombre de la variable.

## Dao

Para acceder a los datos mediante la definición de objetos en Room, se deberá de crear una interfaz
que deberá de llevar
la anotación: `@Dao` en la parte superior, como en el ejemplo siguiente:
Este archivo deberá ser guardado en la ruta (Paquete)>data>datasourse>
database>[dao](../data/datasource/database/dao).

### Kotlin

```kotlin
@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMovie(movieEntity: MovieEntity)

    @Update
    fun updateMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("DELETE FROM movie")
    fun deleteMovies()

    @Query("UPDATE movie SET `like` = :check WHERE id == :id")
    fun updateMovie(id: Int, check: Boolean)

    @Query("SELECT COUNT(*) FROM movie")
    fun countMovies(): Int
}
```

Consulte [MovieDao](../data/datasource/database/dao/MovieDaoKts.kt) para visualizarlo mejor.

### Java

```java

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> getMovies();

    @Query("DELETE FROM movie")
    void deleteMovies();

    @Query("UPDATE movie SET `like` = :check WHERE id == :id")
    void updateMovie(int id, boolean check);

    @Query("SELECT COUNT(*) FROM movie")
    int countMovies();
}
```

Room se encargará crear las implementaciones de cada una de las dao declaradas en está ruta.

## Base de datos

En la ruta (Paquete)>data>[db](../data) se definirá la base datos con la anotación `@Database`, y
dentro de la anotación
se deberán especificar las entidades y la versión de la base de datos (de forma obligatoria), de
forma opcional, se
puede agregar la exportación de la base de datos y las migraciones.

A su vez, las dao deberán ser definidas en este mismo archivo.

### Kotlin

```kotlin
@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): MovieDao
}
```

Consulte [AppDatabase](../data/db/AppDatabaseKts.kt) para visualizarlo mejor.

### Java

```java

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao quoteDao();
}
```