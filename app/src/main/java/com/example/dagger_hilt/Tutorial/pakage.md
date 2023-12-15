# Estructura de los paquetes

Crear la siguiente estructura de carpetas que representaran los paquetes (cada carpeta tiene 3
puntos representando el espacio de paquetes).

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

A continuación, se describe el rol que juega el paquete (o carpeta) en la jerarquía de carpetas
comenzando por sus paquetes principales data y sys.

## Paquetes correspondientes a data

### (Paquete)>data

- **datasource** Accesos a datos.
- **db** Accesos a datos, esta se usa cuando hay una o más bases de datos (solo sucede en raras
  ocasiones).

### (Paquete)>data>datasource

- **database** Fuente de los datos desde la Base de datos SQLite.
- **memory** Fuente de datos desde SharedPreference.
- **web** Fuente de datos desde Web (por ejemplo: Retrofit realizando peticiones al servidor).

### (Paquete)>data>datasource>datababase

- **dao** Consultas a la base de datos; como las querys.
- **entities** Entidades que conforman la db y que usa la dao.

### (Paquete)>data>datasource>web

- **api** Consultas para el consumo de recursos web.
- **models** Pojos o modelos usados para realizar peticiones a los servicios.

## Paquetes correspondientes a la carpeta de sys

### (Paquete)>sys

- **di** Interfaces que controlan la inyección de dependencias.
- **utils** Clases de soporte y configuración del sistema.

## Paquetes fuera de data y sys

### (Paquete)

- **data** IO de datos.
- **domain** Repositorios (decide la fuente de datos usando el paquete data).
- **sys** Responsables de la configuración general del sistema.
- **ui** Interfaz de usuario.
