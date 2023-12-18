# Como Correr el proyecto

Para ejecutar el proyecto utilizando solo clases escritas en Kotlin o Java, puedes realizar ajustes
simples en el archivo `AndroidManifest.xml`.

## Ejecución solo con clases en Kotlin:

Si deseas ejecutar el proyecto utilizando solo las clases escritas en Kotlin, agrega la extensión "
Kts" a todas las referencias de las clases Kotlin en el `AndroidManifest.xml`.

Por ejemplo:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />

    <application android:name=".HiltApplicationKts" android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules" android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true" android:theme="@style/Theme.DaggerHilt" tools:targetApi="31">
        <activity android:name=".ui.activity.singup.SingInActivityKts" android:exported="false" />
        <activity android:name=".ui.activity.splash.SplashActivityKts" android:exported="false" />
        <activity android:name=".ui.activity.main.MainActivityKts" android:exported="false" />
        <activity android:name=".ui.activity.login.LoginActivityKts" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## Ejecución solo con clases en Java:

Por otro lado, si prefieres ejecutar el proyecto utilizando solo las clases escritas en Java,
elimina la extensión "Kts" de las referencias a las clases Kotlin en el `AndroidManifest.xml`. Por
ejemplo:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />

    <application android:name=".HiltApplication" android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules" android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true" android:theme="@style/Theme.DaggerHilt" tools:targetApi="31">
        <activity android:name=".ui.activity.singup.SingInActivity" android:exported="false" />
        <activity android:name=".ui.activity.splash.SplashActivity" android:exported="false" />
        <activity android:name=".ui.activity.main.MainActivity" android:exported="false" />
        <activity android:name=".ui.activity.login.LoginActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

Además, ten en cuenta que al cambiar entre las versiones Kotlin y Java, es necesario ajustar las
anotaciones en la clase `HiltApplication`:

- **Para Kotlin:** Comenta la anotación `@HiltAndroidApp` en `HiltApplication` y asegúrate de
  descomentarla en `HiltApplicationKts`.
- **Para Java:** Comenta la anotación `@HiltAndroidApp` en `HiltApplicationKts` y asegúrate de
  descomentarla en `HiltApplication`.

Esta alternancia permite ejecutar el proyecto con las clases escritas en el lenguaje de programación
que desees, ya sea Kotlin o Java. Recuerda realizar estos cambios en el `AndroidManifest.xml` y en
las anotaciones correspondientes en las clases `HiltApplication` y `HiltApplicationKts` según el
lenguaje que desees utilizar.

> No te olvides de Limpiar el proyecto antes de cambiar entre ambos lenguajes. Para limpiar dirigete
> a Build/CleanProject. De lo contrario, el proyecto puede marcar errores con el codigo que genera
> Dagger-Hilt en tiempo de compilación.