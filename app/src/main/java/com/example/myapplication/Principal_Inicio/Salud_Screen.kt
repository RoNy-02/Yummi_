package com.example.myapplication.Principal_Inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.componentes.CustomBottomBar
import com.example.myapplication.ui.theme.*

@Composable
fun Salud_Screen(navController: NavHostController) {
    Scaffold(
        bottomBar = { CustomBottomBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BgGradientStart, BgGradientEnd)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mi Nutrición",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainPurple
                    )
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Último Mes",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Caloric History Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Historial Calórico", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("Promedio semanal", fontSize = 12.sp, color = TextGray)
                            }
                            Icon(Icons.Default.Whatshot, contentDescription = null, tint = MainOrange)
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Simple Bar Chart Placeholder
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            val heights = listOf(0.6f, 0.7f, 0.9f, 0.65f, 0.8f)
                            heights.forEachIndexed { index, h ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .width(30.dp)
                                            .fillMaxHeight(h)
                                            .background(
                                                if (index == 4) MainOrange else PurpleGrey80,
                                                RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(if(index == 4) "Actual" else "Sem ${index + 1}", fontSize = 10.sp, color = TextGray)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Macronutrients Card
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Macronutrientes", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            // Simple Circle Placeholder
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .background(Color.White, CircleShape)
                            ) {
                                CircularProgressIndicator(
                                    progress = 0.7f,
                                    modifier = Modifier.fillMaxSize(),
                                    color = MainPurple,
                                    strokeWidth = 8.dp
                                )
                                Icon(Icons.Default.PieChart, contentDescription = null, modifier = Modifier.align(Alignment.Center), tint = MainPurple)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            MacroRow("Proteína", "(35%)", Color(0xFFFFB74D))
                            MacroRow("Carbos", "(30%)", Color(0xFF6141A6))
                            MacroRow("Grasas", "(35%)", Color(0xFFB39DDB))
                        }
                    }

                    // Water Card
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF42A5F5))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("1.8 L", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E88E5))
                            Text("Agua hoy", fontSize = 12.sp, color = TextGray)
                            Spacer(modifier = Modifier.height(16.dp))
                            LinearProgressIndicator(
                                progress = 0.6f,
                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                                color = Color(0xFF42A5F5),
                                trackColor = Color(0xFFE3F2FD)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MacroRow(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
        Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(4.dp))
        Text("$label $value", fontSize = 10.sp, color = TextGray)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Salud_ScreenPreview() {
    MyApplicationTheme {
        Salud_Screen(rememberNavController())
    }
}
