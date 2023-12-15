# Inicio de Implementación de inyección de dependencias con hilt

Para comenzar se debe agregar una clase que extienda de Application en el paquete principal de la
aplicación anotada con `@HiltAndroidApp`, con motivo de activar la generación de código con Hilt.

## Kotlin

```kotlin
package com.example.dagger_hilt

import dagger.hilt.android.HiltAndroidApp
import android.app.Application

@HiltAndroidApp
class HiltApplication : Application()
```

## Java

```java
import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HiltApplication extends Application {
}
```

[Vea HiltApplication para más](../HiltApplication.kt).

Después, pasaremos a definir la clase como entrada de aplicación en el manifestó con la
etiqueta `name`, dentro del `application`.

```xml
<application
    android:name=".HiltApplication"/>
```

[Vea Manifest para más aclaraciones](../../../../../AndroidManifest.xml)