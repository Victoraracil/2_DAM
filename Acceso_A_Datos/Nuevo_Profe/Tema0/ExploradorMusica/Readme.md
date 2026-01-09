# Explorador de Música (MP3 – ID3v1 – Serialización)

## Módulo
Acceso a Datos – 2º DAM

## Descripción
Esta aplicación Java permite explorar uno o varios directorios en busca de archivos MP3,
leer sus metadatos ID3v1 y almacenar dicha información en un fichero binario mediante
serialización.  
Posteriormente, la aplicación permite leer el fichero binario generado y mostrar por consola
la información almacenada.

El desarrollo de la práctica se ha realizado utilizando exclusivamente los contenidos vistos
en el **Tema 1 – Ficheros**, aplicando buenas prácticas de estructuración, documentación y
manejo de errores.

---

## Requisitos
- Java JDK 23
- Maven
- IntelliJ IDEA (u otro IDE compatible)

No se utilizan librerías externas adicionales.

---

## Estructura del proyecto

ExploradorMusica/
├─ pom.xml
├─ rutas.txt
├─ canciones.bin
├─ mp3/ (directorios con archivos MP3)
└─ src/
└─ main/
└─ java/
└─ es/accesodatos/mp3/
├─ Main.java
├─ Song.java
├─ ID3v1Reader.java
├─ Mp3Scanner.java
└─ BinaryManager.java

## Funcionamiento de la aplicación

La aplicación dispone de **dos modos de ejecución**, seleccionados mediante argumentos por
línea de comandos.


### Modo `-E` (Escanear)

#### Funcionamiento
- Lee un archivo de texto que contiene **rutas a directorios**, una por línea.
- Recorre dichos directorios de forma **recursiva**.
- Detecta archivos con extensión `.mp3`.
- Lee la cabecera **ID3v1** (últimos 128 bytes del archivo).
- Extrae los siguientes campos:
    - Título
    - Artista
    - Álbum
    - Año
    - Comentario
    - Género
- Guarda la información junto con la ruta del archivo en un fichero binario mediante
  **serialización**.
- Si el fichero binario ya existe, se elimina y se genera uno nuevo.

---

### Modo `-L` (Leer)

#### Funcionamiento
- Lee el fichero binario generado en una ejecución anterior.
- Deserializa la información.
- Muestra por consola los datos de todas las canciones almacenadas.

---

## Archivo `rutas.txt`

El archivo `rutas.txt` contiene **rutas a directorios**, una por línea.  
Se recomienda utilizar **rutas relativas** para facilitar la portabilidad del proyecto.

Ejemplo: mp3

Esto indica que la aplicación debe escanear el directorio `mp3` y todos sus subdirectorios.

---

## Decisiones de diseño

- Se ha separado la lógica en distintas clases para mejorar la claridad y mantenibilidad:
    - `Main`: gestión de argumentos y modos de ejecución.
    - `Mp3Scanner`: recorrido recursivo de directorios.
    - `ID3v1Reader`: lectura de cabeceras ID3v1.
    - `Song`: modelo de datos serializable.
    - `BinaryManager`: gestión de ficheros binarios.
- Se utiliza `RandomAccessFile` para acceder directamente al final de los archivos MP3,
  evitando leer el fichero completo.
- Se emplea `try-with-resources` para asegurar el cierre correcto de los flujos.
- Se devuelve siempre una lista vacía en lugar de `null` para evitar errores.

---

## Limitaciones

- Solo se soporta el estándar **ID3v1**.
- Los archivos MP3 que no contienen cabecera ID3v1 son ignorados.
- El género se muestra como código numérico según el estándar.

---

## Posibles mejoras

- Soporte para ID3v2.
- Mostrar el nombre del género en lugar del código.
- Interfaz gráfica.
- Exportación de datos a otros formatos (JSON, XML).




