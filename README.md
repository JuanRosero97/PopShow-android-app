# Intro

Se implementó el GUI del App centralizando los recursos en los archivos respectivos (strings, images, colors, etc.). Y se brindó soporte para 2 idiomas (español e inglés). Se implementó la capa de acceso a datos del App usando una BD en SQLite que permite almacenar los sitios favoritos seleccionados por el usuario de manera local en el dispositivo y la cual permite hacer CRUD de cada sitio. Además, se implementó las capacidades de conexión HTTP para descargar la información de los sitios turísticos desde un Web Server usando el formato XML. La consulta de la información de un sitio turístico tambien se puede hacer escaneando el código QR de cada localización.

## Screenshots

* Splash screen. 

 ![Splash screen](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc1.jpg)

* Screen principal. Lista desplegable de los lugares turísticos de la ciudad de popayán.
  
 ![principal](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc2.jpg)

* Sitio turístico. Se despliega el sitio turístico seleccionado por el usuario donde se muestran: Imágenes, botón de favorito y la descripción gracias a la respuesta del servidor.
  
 ![sitio](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc3.jpg)

 * Listado de sitios turísticos favoritos del usuario.
  
 ![favoritos](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc4.jpg)

 * Estrellas del sitio turístico.

 ![estrellas](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc5.jpg)

# Introducción a Android Studio

Este proyecto se inició con [Android Studio](https://developer.android.com/studio).

#### Ejemplo:  
Clona este repositorio. Necesitará que `npm` y `Android Studio` estén instalados en su máquina.  

## Instalación:

En el directorio principal del proyecto:
1. Ejecuta el código `npm install -g http-server`
2. Ejecuta el código `http-server`
3. Seleccione la dirección IP local (pero real) y número de puerto como este 
   `http://192.168.X.X`
4. Reemplaza esta dirección en la linea de código ubicada en `res/values/strings/`
![server](https://github.com/JuanRosero97/PopShow-android-app/blob/master/SCREENSHOTS/sc6.jpg)

5. Ejecuta la app o construye la apk en Android Studio

## Acerca de mí:

Mi nombre es Juan Rosero, soy ingeniero y siempre me ha gustado el desarrollo y la programación, y me gustan los diferentes campos de estudio, en la gran mayoría de ellos soy empírico, pero me gusta solucionar problemas en el menor tiempo y con la mejor calidad.



