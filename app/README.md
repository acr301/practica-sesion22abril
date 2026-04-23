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
