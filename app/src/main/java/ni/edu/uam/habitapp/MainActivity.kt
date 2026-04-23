package ni.edu.uam.habitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ni.edu.uam.habitapp.data.HabitMock
import ni.edu.uam.habitapp.ui.components.*
import ni.edu.uam.habitapp.ui.theme.HabitAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DashboardScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
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
            WeeklySummarySection()
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

        FloatingActionButton(
            onClick = { /* Simular agregar hábito */ },
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
        DashboardScreen()
    }
}
