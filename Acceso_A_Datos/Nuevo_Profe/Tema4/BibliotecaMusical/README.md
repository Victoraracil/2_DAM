# Biblioteca Musical — Práctica Acceso a Datos 
--Para esta practica se ha usado IA para la generacion de todo el README, asi como para dudas puntuales y mejoras de la practica.

## Descripción general

La aplicación **Biblioteca Musical** es una aplicación Java desarrollada con **Maven** que permite importar un catálogo de canciones desde un fichero JSON desnormalizado y almacenarlo en una base de datos relacional **PostgreSQL**.

El objetivo principal es aplicar correctamente **JDBC**, la gestión manual de **claves foráneas** y el uso de **transacciones** para garantizar la integridad de los datos.

La aplicación también permite listar la información almacenada en la base de datos mediante distintos modos de ejecución.

---

## Objetivos de la práctica

- Uso correcto de JDBC sin frameworks ORM
- Normalización de datos a partir de un JSON desnormalizado
- Gestión manual de claves primarias y foráneas
- Uso explícito de transacciones (commit y rollback)
- Separación del código en capas (modelo, acceso a datos y lógica)

---

## Tecnologías utilizadas

- Java 21
- Maven
- PostgreSQL
- JDBC
- Jackson (lectura de JSON)
- IntelliJ IDEA

---

## Dependencias (copiadas del pom)

      <artifactId>junit</artifactId>
      <version>3.8.1</version>

      <artifactId>postgresql</artifactId>
      <version>42.7.2</version>

      <artifactId>jackson-databind</artifactId>
      <version>2.17.0</version>
---

## Estructura del proyecto

El proyecto sigue una arquitectura por capas:

model/
├─ Artist
├─ Album
├─ Track
├─ Genre
└─ JsonTrack

dao/
├─ ArtistDAO
├─ AlbumDAO
├─ TrackDAO
└─ GenreDAO

db/
└─ DBConnection

service/
└─ MusicImportService

Main


### Capas

**model**  
Contiene las clases POJO que representan las entidades de la base de datos:
- Artist
- Album
- Track
- Genre

Además, existe la clase `JsonTrack`, que se utiliza únicamente para mapear el contenido del JSON.  
Esta clase no representa una tabla de la base de datos.

**dao**  
Contiene las clases encargadas de ejecutar las consultas SQL usando JDBC.  
Cada DAO se encarga exclusivamente de una tabla y no contiene lógica de negocio.

**db**  
Contiene la clase `DBConnection`, que centraliza la creación de la conexión JDBC con PostgreSQL.

**service**  
Contiene la clase `MusicImportService`, que orquesta el proceso de importación del JSON y la gestión de transacciones.

**Main**  
Contiene el punto de entrada de la aplicación y gestiona los modos de ejecución mediante argumentos.

---

## Base de datos

La base de datos se llama `Music_Library` y contiene las siguientes tablas:

- `genre (id, name)`
- `artist (id, name)`
- `album (id, title, year, artist_id)`
- `track (id, title, track_number, duration, album_id, genre_id)`

Características importantes:
- `genre.name` y `artist.name` tienen restricción `UNIQUE` para evitar duplicados.
- `album` tiene una restricción `UNIQUE (title, artist_id)`, lo que permite que distintos artistas tengan álbumes con el mismo nombre.
- `track` contiene claves foráneas hacia `album` y `genre`.

---

## Conexión JDBC

La conexión a la base de datos se gestiona mediante una única clase llamada `DBConnection`.

Esta clase devuelve un objeto `Connection` usando `DriverManager`.

La clase de conexión **no controla transacciones**.  
El control de `commit` y `rollback` se realiza en la capa de lógica de negocio.

---

## Uso de PreparedStatement y signos de interrogación (?)

En todos los DAOs se utilizan **PreparedStatement** con parámetros representados por signos de interrogación (`?`).

Ejemplo:

INSERT INTO artist (name) VALUES (?)


Los signos de interrogación representan valores que se asignan posteriormente mediante métodos como `setString` o `setInt`.

### Motivos para usar PreparedStatement

- Evita inyección SQL
- Maneja automáticamente comillas y tipos de datos
- Mejora el rendimiento al reutilizar consultas precompiladas
- Es la forma correcta y profesional de trabajar con JDBC

Por estos motivos, no se utilizan concatenaciones de Strings para construir consultas SQL.

---

## Transacciones

Cada canción del JSON se importa dentro de **su propia transacción**.

Para cada canción:
1. Se desactiva el `autoCommit`
2. Se insertan o reutilizan género, artista y álbum
3. Se inserta la canción
4. Si todo va bien, se ejecuta `commit`
5. Si ocurre cualquier error, se ejecuta `rollback`

Este enfoque garantiza:
- Integridad referencial
- Ausencia de datos huérfanos
- Coherencia en la base de datos

**Una canción = una transacción**

---

## Importación del JSON

El fichero JSON contiene información desnormalizada, es decir, cada objeto incluye datos de canción, artista, álbum y género.

El proceso de importación sigue este orden:

1. Comprobar si el género existe. Si no existe, insertarlo y recuperar su ID.
2. Comprobar si el artista existe. Si no existe, insertarlo y recuperar su ID.
3. Comprobar si el álbum existe para ese artista. Si no existe, insertarlo y recuperar su ID.
4. Insertar la canción usando los IDs obtenidos.

Este proceso se repite para cada canción del JSON dentro de una transacción independiente.

---

## Modos de ejecución

### Importar datos
-I ruta_fichero.json

Ejemplo:
-I CatálogoCanciones.json


### Listar datos
-L -C Lista canciones
-L -A Lista artistas
-L -B Lista álbumes
-L -G Lista géneros


---

## Ejecución desde IntelliJ

El proyecto es Maven y se puede ejecutar directamente desde IntelliJ.

Los argumentos se configuran en la configuración de ejecución de la clase `Main`.

Ejemplo:
-I CatálogoCanciones.json


---

## Limitaciones

- No se controla la duplicación de canciones
- No se valida el contenido del JSON
- No existe interfaz gráfica

---

## Trabajo futuro

- Control de duplicados de canciones
- Conversión de duración a segundos
- Uso de DAOs también para los listados
- Añadir interfaz gráfica o API REST

---

## Conclusión

La aplicación cumple todos los requisitos de la práctica:

- Uso correcto de JDBC
- Normalización de datos
- Gestión manual de claves foráneas
- Uso correcto de transacciones
- Diseño limpio y modular

El proyecto es funcional, coherente y fácilmente defendible desde el punto de vista técnico.