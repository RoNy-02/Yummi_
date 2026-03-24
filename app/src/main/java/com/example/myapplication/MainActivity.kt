package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Principal_Inicio.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "In_session") {
        composable("In_session") {
            Inicio_sesion(navController = navController)
        }
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }
        composable("RegisterScreen") {
            RegisterScreen(navController)
        }
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("RefriScreen") {
            Refri_Screen(navController)
        }
        composable("SaludScreen") {
            Salud_Screen(navController)
        }
        composable("PerfilScreen") {
            Perfil_Screen(navController)
        }
        composable("AchievementsScreen") {
            AchievementsScreen()
        }
        composable("SettingsScreen") {
            SettingsScreen()
        }
    }
}
