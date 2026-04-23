package ni.edu.uam.habitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

enum class HabitCategory(val color: Color) {
    Health(Color(0xFF81C784)),
    Study(Color(0xFF64B5F6)),
    Work(Color(0xFFFFB74D))
}

data class Habit(
    val id: Int,
    val name: String,
    val goal: String,
    val isCompleted: Boolean,
    val category: HabitCategory
)

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    var habits by remember {
        mutableStateOf(
            listOf(
                Habit(1, "Beber agua", "2L", false, HabitCategory.Health),
                Habit(2, "Estudiar Kotlin", "1h", true, HabitCategory.Study),
                Habit(3, "Ejercicio", "30 min", false, HabitCategory.Health),
                Habit(4, "Revisar correos", "9:00 AM", true, HabitCategory.Work)
            )
        )
    }

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

@Composable
fun HeaderSection(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Hola, $name 👋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
        }
    }
}

@Composable
fun ProgressCard(progress: Float) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progressAnimation")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(
                    text = "Progreso de hoy",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterEnd),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun WeeklySummarySection() {
    val days = listOf("L", "M", "X", "J", "V", "S", "D")
    val completedDays = listOf(true, true, false, true, false, false, false)

    Column {
        Text(
            text = "Resumen semanal",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEachIndexed { index, day ->
                val isCompleted = completedDays[index]
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                if (isCompleted) Color(0xFF4CAF50) else Color.LightGray.copy(alpha = 0.4f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            color = if (isCompleted) Color.White else Color.DarkGray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitListSection(habits: List<Habit>, onHabitToggle: (Int) -> Unit) {
    Column {
        Text(
            text = "Mis Hábitos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(habits) { habit ->
                HabitItem(habit = habit, onToggle = { onHabitToggle(habit.id) })
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onToggle: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (habit.isCompleted) habit.category.color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        label = "colorAnimation"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onToggle() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (habit.isCompleted) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = habit.category.color,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (habit.isCompleted) Color.Gray else Color.Unspecified
            )
            Text(
                text = habit.goal,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(habit.category.color)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    HabitAppTheme {
        DashboardScreen()
    }
}
