package com.example.myapplication.Principal_Inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Refri_Screen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    val items = listOf(
        RefriItemData("Leche Deslactosada", "1 Lt • Exp: 2026-02-05", Color(0xFFF5F5F5)),
        RefriItemData("Espinacas", "300g • Exp: 2026-02-03", Color(0xFFE8F5E9)),
        RefriItemData("Huevos", "12 pzas • Exp: 2026-02-15", Color.White),
        RefriItemData("Yogurt Griego", "1 bote • Exp: 2026-02-04", Color(0xFFFFF3E0))
    )

    val filteredItems = items.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MainOrange,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan")
            }
        },
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
                Text(
                    text = "Mi Refri",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainPurple
                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar alimento...", color = TextGray, fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredItems) { item ->
                        RefriItemRow(item)
                    }
                }
            }
        }
    }
}

data class RefriItemData(val name: String, val details: String, val bgColor: Color)

@Composable
fun RefriItemRow(item: RefriItemData) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(item.bgColor, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextBlack)
                Text(item.details, fontSize = 12.sp, color = TextBlack)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = TextGray)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Refri_ScreenPreview() {
    MyApplicationTheme {
        Refri_Screen(rememberNavController())
    }
}
