package com.example.myapplication.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.theme.MainPurple
import com.example.myapplication.ui.theme.TextGray

@Composable
fun CustomBottomBar(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(
        containerColor = Color.White,
        contentColor = TextGray,
        tonalElevation = 8.dp,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Inicio",
                isSelected = currentRoute == "HomeScreen",
                onClick = { navController.navigate("HomeScreen") }
            )
            BottomNavItem(
                icon = Icons.Default.ShoppingCart,
                label = "Refri",
                isSelected = currentRoute == "RefriScreen",
                onClick = { navController.navigate("RefriScreen") }
            )
            
            // Center Floating Button
            Box(
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .size(60.dp)
                    .background(MainPurple, CircleShape)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Perfil",
                isSelected = currentRoute == "PerfilScreen",
                onClick = { navController.navigate("PerfilScreen") }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    androidx.compose.material3.IconButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) MainPurple else TextGray
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = if (isSelected) MainPurple else TextGray
            )
        }
    }
}
