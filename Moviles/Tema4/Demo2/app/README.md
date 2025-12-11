# Práctica Kotlin: Gestión de Recetas

## Objetivo
Esta práctica consiste en crear una app para gestionar recetas, con un listado, un formulario para añadir nuevas recetas y una pantalla de detalle. Además, se debía mover todo el texto a `strings.xml` para soportar internacionalización y permitir eliminar recetas con pulsación larga o menú contextual.

---

## Cambios realizados

### 1. Internacionalización
Todos los textos fijos se pasaron a `strings.xml`:

- **Pantallas:** `Listado de Recetas`, `Añadir Receta`, `Detalle de la Receta`.
- **Botones:** `Añadir Receta`, `Confirmar`, `Cancelar`.
- **Mensajes:** confirmación de eliminación, validación de formulario, receta eliminada, sin receta seleccionada.
- **Menú contextual:** `Más opciones`, `Eliminar Receta`.
- **Otros:** etiquetas de nombre y descripción de la receta.

### 2. Eliminación de recetas
Se implementó de dos formas:

1. **Pulsación larga:** al mantener pulsado un elemento, aparece un diálogo de confirmación. Confirmando, se elimina la receta.
2. **Menú contextual:** cada elemento tiene un icono “Más opciones” que abre un `DropdownMenu` con la opción `Eliminar Receta`. También muestra el diálogo antes de eliminar.

### 3. Diálogo de confirmación
Se reutiliza el mismo `AlertDialog` en `MainActivity` para ambas acciones, usando los botones `Confirmar` y `Cancelar`.

### 4. Ficheros modificados
- `MainActivity.kt`: se añadieron las llamadas al diálogo y se ajustaron los strings.
- `strings.xml`: se movieron todos los textos fijos.
- `ListScreen.kt`: se implementó `combinedClickable` y el menú contextual.

---

La app ahora permite eliminar recetas de manera segura y está lista para soportar varios idiomas. Todo compila y funciona correctamente.
