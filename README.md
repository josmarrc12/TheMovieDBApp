<h1 align="center">
TheMovieDBAPP
</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/API-24+-yellow.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/Kotlin-2.0-orange.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/Architecture-MVVM-purple.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/Tecnología-Jetpack Compose-green.svg"/></a>
</p>

<p>
🎞️ TheMovieDBApp demuestra el desarrollo moderno de Android con <b>Jetpack Compose, Hilt,  Coroutines, Flow, Jetpack (Room, Viewmodel), y Material Design</b>, basado en la arquitectura <b>MVVM</b> 
</p>

<h2>
Pantalla principal
</h2>


Permite ver las portadas de las peliculas con sus diferentes categorias y con una interfaz muy amigable con el usuario, en esta sección se hace uso de <b>Paging3</b> para generar el listado de los items, de esta forma cargamos los datos de manera eficiente y reactiva, con esto logramos:
- Evitar cargas innecesarias de datos.
- Reducir el uso de memoria al cargar solo lo necesario.
- Brindar una experiencia de usuario fluida


|  <img src="https://github.com/user-attachments/assets/2182ce7e-aa34-416f-a1d1-b111af14ee8e" style="height: 50%; width:50%;"/> |  <img src="https://github.com/user-attachments/assets/947e48e0-68c3-4f1a-aedb-98ccb523b98e" style="height: 50%; width:50%;"/> |
| :------------: | :------------: |
| <img src="https://github.com/user-attachments/assets/4622f869-799f-43e5-b2de-e1d0ea066d38" style="height: 50%; width:50%;"/>  |  <img src="https://github.com/user-attachments/assets/3a2d9e1a-8d64-4bf4-aa6d-4b18908ff7ad" style="height: 50%; width:50%;"/> |

<h2>
Detalles
</h2>

<p>
Permite ver la información de la película seleccionada, aquí utilizo <b>Coil</b> para mostrar la imagen, y se muestra un botón de marcador para guardar la información con <b>Room</b>
</p>
<p align="center">
	<img src="https://github.com/user-attachments/assets/add9bcbd-2194-46f9-ac7f-3cca97b55dc9" style="height: 50%; width:50%;"/>
</p>


<h2>
Cambio de idioma
</h2>

<p>
Desde el menú principal, es posible acceder a la configuración de idioma. Al iniciar la aplicación por primera vez, se utiliza el idioma predeterminado del sistema operativo. Sin embargo, el usuario puede cambiarlo en cualquier momento al idioma de su preferencia. Esta preferencia se guarda utilizando <b>Proto DataStore</b>, lo que permite mantener el idioma seleccionado de forma persistente y aplicarlo correctamente.</p>
<p align="center">
	<img src="https://github.com/user-attachments/assets/ce01a2e6-4f0c-478b-9bcd-267acc18c23d" style="height: 50%; width:50%;"/>
</p>

<h2>
Marcadores
</h2>

<p>
La pantalla de marcadores permite visualizar las películas guardadas previamente desde la pantalla de detalles. Aquí se realiza una consulta a la base de datos local utilizando <b>Room</b>, y también se ofrece la opción de eliminar registros. La interfaz es intuitiva y fácil de usar.<p align="center">
	<img src="https://github.com/user-attachments/assets/74402271-5519-4aae-b295-e2bdd77f4cbe" style="height: 50%; width:50%;"/>
</p>

<h2>
API
</h2>


TheMovieDBApp usa la API de [TMDB](https://www.themoviedb.org/) para construir una API RESTful. <br>
TMDB proporciona una interfaz de API RESTful para acceder a objetos detallados construidos a partir de grandes volúmenes de datos relacionados con películas.


<h2>
Librerías
</h2>

- Retrofit
- Dagger Hilt
- Room
- Paging3
- Coroutines
- Okhttp3
- Retrofit
- Hilt Navigation 
- Coil
- Proto DataStore
- Secrets Gradle Plugin


