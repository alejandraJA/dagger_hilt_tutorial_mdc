## Instrucciones

Se debera crear una base de Android (Usando preferentemente MVVM) el cual debera de tener las
siguientes funcionalidades.

* Un servicio de red que debera conectarse al sigiuentes servicio REST, con un retraso de cuatro
  segundos;

  GET  https://api.datos.gob.mx/v1/gobmx.facts


* Debera de contar con el uso de activity y fragmentos donde en la primer vista se debe mostrar lo
  siguiente
    * En la primer vista mostrar un splash con loader y un mensaje de carga
    * Una vez terminado el consumo sustituir por una lista donde se muestren 3 datos de cada
      elemento del consumo.
    * En caso de fallar el consumo poner una alerta con un id y mensaje (Se deberan controlar 2
      casos minimo, falla en red y falla en el consumo.)

El Objetivo es:

	- Llame al servicio de red cuando la aplicación termine de iniciarse y muestre el indicador de 
    carga mientras la llamada está pendiente
	- Mostrar algunos contenidos de cada objeto JSON en una lista si la llamada es exitosa
    - Los datos se deben consumir una unica vez y se deben guardar en una base de datos de room   
	- Paginar la respuesta del servicio REST, en donde se muestres 10 registros por pagina dentro 
    de la pantalla (forma de paginación libre)
	- Si la llamada de red falla, muestra un error al usuario.
	- Colocar un filtro de busqueda por texto que busque por cualquier palabra en el campo 
    organizacion y muestre las coincidencias en pantalla.
	- Al dar click en un elemento mostrar toda la información del elemento (la forma de 
    mostrarlo es libre) y contar con un boton de compartir la informacion mediante whatsapp.
    - Al dar click en el boton compartir se debe compartir la información del elemento 
    junto con la latitud y longitud donde se encuentra el usuario.
    - La aplicacion debe tener un login (El usuario puede hacer login con user/pass y con google)
    - Debera existir un check para habilitar login con huella.
    - Se debera cambiar el icono de la app por el icono de su iniciativa.

Sugerencias:

- La aplicacion debe ser estrictamente en vertical.
- La aplicacion debe ser responsiva.
- Se deben solicitar permisos siempre al iniciar la aplicación a menos que ya se hayan otorgado.
- Toda clase debe tener formato
- Todos en ingles.

Aspectos a calificar:
-

- Estructura del codigo
- Limpieza del codigo.
- Pruebas unitarias de almenos un 90% del codigo.
- Uso de navigation.
