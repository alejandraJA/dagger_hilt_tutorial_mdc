# Implementación

Los modulos se colocarán en la ruta (Paquete)>sys>[di](../sys/di).

## Módulo de la base de datos

### Kotlin

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext app: Context?) = Room.databaseBuilder(
        app!!,
        AppDatabase::class.java,
        "movies"
    ).build()

    @Singleton
    @Provides
    fun provideQuotesDao(db: AppDatabase): MovieDao = db.quoteDao()
}
```

Consulte [DatabaseModule](../sys/di/DatabaseModule.kt).

### Java

```java

@Module
@InstallIn(SingletonComponent.class)
public abstract class DatabaseModule {
    @Singleton
    @Provides
    public static AppDatabase providerDatabase(@ApplicationContext Context app) {
        return Room.databaseBuilder(
                app,
                AppDatabase.class,
                "movies"
        ).build();
    }

    @Singleton
    @Provides
    public static MovieDao provideQuotesDao(AppDatabase db) {
        return db.quoteDao();
    }

}
```

Ambos métodos están anotados con las etiquetas `@Provides` y `@Singleton`, la primera sirve para
establecer el enlace al método, mientras que la segunda específica que siempre establecerá la misma
instancia y que no creará más instancias del mismo tipo de dato.

En este módulo se crea la instancia para la base de datos que, a su vez, será consumida por el
método que provee la Dao, para poder obtener la Dao y proveerla a los repositorios.

## Módulo del web Service

Aquí crearemos las instancias para Retrofit, el cliente que usará retrofit (tomando en cuenta las
configuraciones para el interceptor en modo debug), el servicio y la HelperImp.

### Kotlin

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
        return build
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieHelper(movieHelperImp: MovieHelperImp): MovieHelper = movieHelperImp
}
```

En este, se crea la instancia del cliente (tomando en cuenta las configuraciones del interceptor en
modo debug), y este cliente será consumido por la instancia que provee Retrofit, la cual es
consumida por el método que provee el servicio, que proveerá el servicio al HelperImp. Por último,
pero no menos importante, el método que proporciona la instancia del Helper consumirá el HelperImp y
lo proveerá a los repositorios.

### Java

```java

@Module
@InstallIn(SingletonComponent.class)
public abstract class ApiModule {

    @NonNull
    @Singleton
    @Provides
    public static OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            var loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    @Singleton
    @Provides
    public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public static MovieService provideMovieService(@NonNull Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }
}
```

De igual modo que con el modulo de la base de datos, se especifica a Dagger que cree una sola
instancia de cada dato.

En este, se crea la instancia del cliente (tomando en cuenta las configuraciones del interceptor en
modo debug), y este cliente será consumido por la instancia que provee Retrofit, la cual es
consumida por el método que provee el servicio, que proveerá el servicio al repositorio. 