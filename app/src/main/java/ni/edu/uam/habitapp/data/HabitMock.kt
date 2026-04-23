package ni.edu.uam.habitapp.data

import ni.edu.uam.habitapp.model.Habit
import ni.edu.uam.habitapp.model.HabitCategory

object HabitMock {
    val habits = listOf(
        Habit(1, "Beber agua", "2L", false, HabitCategory.Health),
        Habit(2, "Estudiar Kotlin", "1h", true, HabitCategory.Study),
        Habit(3, "Ejercicio", "30 min", false, HabitCategory.Health),
        Habit(4, "Revisar correos", "9:00 AM", true, HabitCategory.Work)
    )
}
