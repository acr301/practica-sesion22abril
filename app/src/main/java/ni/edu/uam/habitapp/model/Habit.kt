package ni.edu.uam.habitapp.model

data class Habit(
    val id: Int,
    val name: String,
    val goal: String,
    val isCompleted: Boolean,
    val category: HabitCategory
)
