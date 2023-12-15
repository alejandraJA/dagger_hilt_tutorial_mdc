# Repositorios

    UI Layer
        ↓
    Domain Layer
        ↓
    Data Layer

Los repositorios representan la capa de dominio, entre la capa de UI y la de datos.

Se encarga de encapsular la lógica empresarial compleja o simple, que los ViewModel reutilizarán.

Esta capa es opcional porque no todas las aplicaciones tendrán estos requisitos. Solo debes usarla
cuando sea necesario; por ejemplo, para administrar la complejidad o favorecer la reutilización.

Una capa de dominio brinda los siguientes beneficios:

- Evita la duplicación de código.
- Mejora la legibilidad en las clases que usan las de la capa de dominio.
- Mejora la capacidad de prueba de la aplicación.
- Evita las clases grandes, ya que te permite dividir las responsabilidades.

Los repositorios siempre llevaran la etiqueta `@Inject` en el constructor, para que Dagger se
encargué de proporcionar las instancias necesarias.