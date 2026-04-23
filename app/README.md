Diseñar una pantalla tipo dashboard donde el usuario vea su progreso diario de hábitos, estilo app de productividad o bienestar.


Indicaciones
Estructura general

    Usa un Column como base.
    Aplica padding general.
    Divide la pantalla en secciones con Spacer.

    Esto debe estar en MainActivity.kt

Sección 1: Header motivacional

    Usa un Row:
        Saludo personalizado:
        👉 “Hola, Ana 👋”
        Icono de notificación 🔔 alineado a la derecha.

    Usa Arrangement.SpaceBetween.

Sección 2: Tarjeta de progreso

Crea una tarjeta principal usando Box:

Dentro incluye:

    Texto: “Progreso de hoy”
    Barra de progreso simulada (puede ser un Box con fondo y otro encima con porcentaje).
    Porcentaje grande (ej: 75%)

    Usa bordes redondeados.
    Fondo con color suave.


Sección 3: Lista de hábitos

Usa Column o LazyColumn.
Cada hábito debe ser un Row:

    Checkbox o ícono de estado
    Nombre del hábito (ej: “Beber agua”)
    Hora o meta (ej: “2L” o “8:00 AM”)

    Cambia color si está completado.
    Usa Spacer para alinear todo.

Sección 4: Resumen semanal

Usa un Row con 7 “círculos” (días de la semana):

    L M X J V S D
    Cada uno indica progreso:
        Verde = completado
        Gris = incompleto


Sección 5: Botón flotante simulado

    Crea un botón “+” en un Box al final.
    Alineado abajo a la derecha.
    Simula que agrega un nuevo hábito.



    Agrega animación de cambio de color al completar un hábito.
    Haz que la barra de progreso cambie dinámicamente.
    Añade un modo oscuro/claro visual (solo diseño).
    Haz que cada hábito tenga una mini categoría con color (salud, estudio, trabajo).

## Implementación: Flujo de Lógica y Decisiones de Diseño

### Flujo de Lógica
1.  **Gestión de Estado**: Se utiliza un `mutableStateOf` dentro de `DashboardScreen` para mantener la lista de hábitos. Esto permite que la UI reaccione automáticamente a cualquier cambio (como marcar un hábito como completado).
2.  **Cálculo de Progreso**: El progreso no se almacena por separado; se calcula dinámicamente cada vez que la lista de hábitos cambia. Esto garantiza que la barra de progreso y el porcentaje mostrado siempre estén sincronizados con la realidad.
3.  **Interactividad**: Al hacer clic en un `HabitItem`, se dispara un callback que actualiza la lista en el estado principal, gatillando una recomposición de los componentes afectados.
4.  **Animaciones**: Se implementaron `animateFloatAsState` para la barra de progreso y `animateColorAsState` para el fondo de los ítems, mejorando la experiencia de usuario con transiciones suaves.

### Decisiones de Diseño
1.  **Modularidad**: La interfaz se dividió en componentes pequeños (`HeaderSection`, `ProgressCard`, `WeeklySummarySection`, `HabitListSection`). Esto facilita el mantenimiento y mejora la legibilidad del código.
2.  **Material Design 3**: Se utilizaron componentes y esquemas de color de Material 3 (`Card`, `FloatingActionButton`, `MaterialTheme.colorScheme`) para asegurar una apariencia moderna y soporte nativo para temas claro/oscuro.
3.  **Jerarquía Visual**:
    *   Se usó un `ProgressCard` prominente para dar feedback inmediato al usuario sobre su éxito diario.
    *   Las categorías de hábitos tienen colores distintos (`Health`, `Study`, `Work`) para permitir una identificación rápida.
    *   El uso de `Spacer` y `Arrangement.SpaceBetween` asegura que la pantalla no se sienta saturada y que los elementos importantes tengan aire.
4.  **Iconografía**: Se utilizaron iconos vectoriales estándar de Material para una estética limpia y profesional.
