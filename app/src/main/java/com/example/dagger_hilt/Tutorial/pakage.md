# Estructura de los paquetes

Crear la siguiente estructura de carpetas que representaran los paquetes.

- nombre.del.paquete
    - data
        - datasource
            - database
                - dao
                - entities
            - memory
            - web
                - api
                - models
        - db
    - domain
    - sys
        - di
        - util
    - ui

La estructura de paquetes propuesta sigue los principios de Clean Architecture, una arquitectura de
software que busca separar las preocupaciones y mantener una estructura clara y modular en una
aplicación. Aquí hay una explicación más detallada de cada paquete y su función dentro de la
jerarquía de carpetas:

### Paquetes correspondientes a `data`

#### `data`

- **datasource:** Gestiona el acceso a los datos, proporcionando diferentes fuentes de datos como
  bases de datos, memoria y recursos web.
- **db:** Se utiliza cuando hay una o más bases de datos, lo cual no es común pero puede ocurrir.

#### `data > datasource`

- **database:** Contiene la lógica para acceder a los datos desde una base de datos local, como
  SQLite. Aquí se encuentran las entidades y DAOs.
- **memory:** Gestiona los datos almacenados en la memoria, como SharedPreferences.
- **web:** Ofrece acceso a datos a través de recursos web, utilizando APIs y modelos específicos
  para las peticiones al servidor.

#### `data > datasource > database`

- **dao:** Contiene las consultas y operaciones sobre la base de datos, como queries y
  actualizaciones.
- **entities:** Define las entidades que representan la estructura de la base de datos y son
  utilizadas por los DAOs.

#### `data > datasource > web`

- **api:** Define las interfaces o clases que realizan peticiones y consumen recursos web
  utilizando, por ejemplo, Retrofit.
- **models:** Contiene los POJOs o modelos que se utilizan para representar los datos recibidos
  desde los servicios web.

### Paquetes correspondientes a `sys`

#### `sys`

- **di:** Contiene las interfaces y configuraciones relacionadas con la inyección de dependencias en
  la aplicación.
- **util:** Almacena clases de soporte y configuración del sistema, como utilidades genéricas,
  clases de manejo de errores, entre otros.

### Otros paquetes fuera de `data` y `sys`

#### Paquete principal

- **data:** Se encarga de la entrada y salida de datos.
- **domain:** Contiene la lógica del dominio de la aplicación, incluyendo los repositorios que
  deciden la fuente de datos utilizando el paquete `data`.
- **sys:** Responsable de la configuración general del sistema y su funcionamiento.
- **ui:** Encargado de la interfaz de usuario, incluyendo todas las capas relacionadas con la
  presentación de la aplicación.

Esta estructura de paquetes ayuda a mantener un código organizado, modular y con una separación
clara de responsabilidades, permitiendo un desarrollo más mantenible, testeable y escalable de la
aplicación.