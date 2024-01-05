# Inyección de Dependencias

La inyección de dependencias es un principio de diseño en programación orientada a objetos y
desarrollo de software. En términos simples, se trata de una técnica que permite crear objetos de
una clase, pero en lugar de que la clase misma se encargue de crear sus propias dependencias 
(objetos con los que interactúa), estas dependencias son proporcionadas desde fuera de la clase.

Imagina que tienes una clase A que necesita utilizar los servicios de una clase B. En lugar de que
la clase A cree una instancia de la clase B dentro de sí misma, se le proporciona una instancia de B
desde fuera, generalmente a través de un mecanismo como parámetros del constructor, métodos o un
contenedor de inversión de control (IoC, por sus siglas en inglés).

Esto tiene varias ventajas:

1. **Desacoplamiento:** La clase A no está fuertemente acoplada a la clase B, lo que facilita
   cambios futuros. Puedes cambiar la implementación de B sin afectar a A, siempre y cuando la
   interfaz siga siendo la misma.

2. **Testeo:** Permite realizar pruebas unitarias más sencillas, ya que puedes proporcionar
   fácilmente objetos simulados (mocks) en lugar de las dependencias reales para probar el
   comportamiento de la clase.

3. **Reutilización y flexibilidad:** Facilita la reutilización de código y hace que el código sea
   más flexible, ya que las dependencias pueden ser intercambiadas fácilmente.

En resumen, la inyección de dependencias es una práctica que promueve un diseño más flexible,
mantenible y testeable al separar la creación y gestión de dependencias del comportamiento de las
clases que las utilizan.