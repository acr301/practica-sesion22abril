package ni.edu.uam.habitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ni.edu.uam.habitapp.data.HabitMock
import ni.edu.uam.habitapp.model.Habit
import ni.edu.uam.habitapp.model.HabitCategory
import ni.edu.uam.habitapp.ui.components.*
import ni.edu.uam.habitapp.ui.theme.HabitAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            
            HabitAppTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        // We will place the FAB inside DashboardScreen Box for more control if needed, 
                        // but standard Scaffold is also fine. Let's keep it in the Box for custom positioning.
                    }
                ) { innerPadding ->
                    DashboardScreen(
                        modifier = Modifier.padding(innerPadding),
                        isDarkTheme = isDarkTheme,
                        onThemeToggle = { isDarkTheme = !isDarkTheme }
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    var habits by remember { mutableStateOf(HabitMock.habits) }

    val completedCount = habits.count { it.isCompleted }
    val progress = if (habits.isNotEmpty()) completedCount.toFloat() / habits.size else 0f

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderSection(name = "Ana")
            Spacer(modifier = Modifier.height(24.dp))
            ProgressCard(progress = progress)
            Spacer(modifier = Modifier.height(24.dp))
            WeeklySummarySection(progress = progress)
            Spacer(modifier = Modifier.height(24.dp))
            HabitListSection(
                habits = habits,
                onHabitToggle = { habitId ->
                    habits = habits.map {
                        if (it.id == habitId) it.copy(isCompleted = !it.isCompleted) else it
                    }
                }
            )
        }

        // Theme Toggle Button (Bottom Left)
        FilledTonalIconButton(
            onClick = onThemeToggle,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Cambiar tema"
            )
        }

        // Add Habit Button (Bottom End)
        FloatingActionButton(
            onClick = {
                val newHabit = Habit(
                    id = habits.size + 1,
                    name = "Nuevo Hábito ${habits.size + 1}",
                    goal = "Meta diaria",
                    isCompleted = false,
                    category = HabitCategory.values().random()
                )
                habits = habits + newHabit
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar hábito")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    HabitAppTheme {
        DashboardScreen(isDarkTheme = false, onThemeToggle = {})
    }
}
