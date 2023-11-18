# Vinilos

## Descripción de la App

Vinilos es una aplicación móvil diseñada exclusivamente para dispositivos Android. Esta app está diseñada para satisfacer las necesidades de los coleccionistas de música, permitiendo a los amantes de la música mantener un registro completo de sus colecciones. Con Vinilos, puedes consultar y registrar datos relacionados con álbumes, colecciones y artistas.

## Como ejecutarla

Para ejecutar la aplicación existen dos alternativas:

### Usar el APK

#### Requisitos

1. Contar con un dispositivo con sistema operativo Android con versión L o superior, versión SDK 22 o superior.
2. Poder ejecutar aplicaciones de fuentes desconocidas sobre ese dispositivo.

#### Pasos:

1. Descargue la última versión disponible en la página [Releases](https://github.com/MISW-4203-2023/Vinilos/releases).
2. Acepte la ejecución de [aplicaciones de fuentes desconocidas](https://developer.android.com/studio/publish?hl=es-419#publishing-unknown).
3. ¡Listo! la aplicación ya está lista para ejecutarse en su teléfono.

### Ejecutar desde el código fuente

#### Requisitos:

1. Docker Ver requisitos y pasos de instalación: [Mac](https://docs.docker.com/desktop/install/mac-install/), [Linux](https://docs.docker.com/desktop/install/linux-install/) y [Windows](https://docs.docker.com/desktop/install/windows-install/)
2. Android Studio Giraffe | 2022.3.1 Patch 2 [Ver Requisitos y pasos de instalación](https://developer.android.com/codelabs/basic-android-kotlin-compose-install-android-studio?hl=es-419#0)

#### Pasos: 

Puede ejecutar la aplicación desde el emulador o a un dispositivo conectado, en el [siguiente enlace](https://developer.android.com/studio/run?hl=es-419) hay documentación adicional acerca de como correr y depurar aplicaciones en android studio.
Para que permita acceder a la información es necesario descargar y ejecutar el backend a continuación se deja el paso a paso para desplegar y ejecutar el backend:

##### Backend

1. Descargar el proyecto de BackVynils del repositorio: https://github.com/MISW-4203-2023/BackVynils este es el backend de la herramienta
2. Asegúrese de tener disponibles los puertos 3000 (para la aplicación web) y 5432 (para la base de datos postgres), puede cambiar estos puertos en el archivo docker-compose.yaml
3. Desde la carpeta raiz de “BackVynils” ejecutar el siguiente comando, para inicializar el back de la aplicación y permitir el acceso a la bd:
  
   ```sh
   docker-compose up –d
   ```
   
5. Debería ver una imagen similar a la siguiente:
<img width="1209" alt="image" src="https://github.com/MISW-4203-2023/Vinilos/assets/124005780/36e6740c-b759-4358-a401-b1b0dc3e5988">

##### Aplicación

1. Descargue el contenido de la rama `main`
2. Abra el proyecto en Android Studio
3. Obtenga la ip local de su computadora, existen comandos como `ifconfig` en sistemas basados en unix y el comando `ipconfig` para sistemas windows.
4. Reemplace la ip en el archivo `network_security_config.xml`

![image](https://github.com/MISW-4203-2023/Vinilos/assets/124005780/0258848b-b808-41ef-b8ec-c95901d2144e)

![image](https://github.com/MISW-4203-2023/Vinilos/assets/124005780/e5f73727-4095-46cd-a3fb-b306ceda044a)

5. Use la ip en el archivo `AppContainer.kt` para apuntar al servicio REST, suponiendo que su ip privada es `192.168.0.10` y que el puerto de la app web de docker es `3000` el valor de la línea 16 será:
   
```kt
  private val baseUrl = "http://192.168.0.10:3000/"
```

![image](https://github.com/MISW-4203-2023/Vinilos/assets/124005780/9e0b40d5-c808-47a7-bc5b-7723e1213bcd)


