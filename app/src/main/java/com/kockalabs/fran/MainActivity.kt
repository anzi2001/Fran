package com.kockalabs.fran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kockalabs.fran.ui.screens.details.DetailsScreen
import com.kockalabs.fran.ui.theme.FranTheme
import com.kockalabs.fran.ui.screens.home.HomeScreen
import com.kockalabs.fran.ui.screens.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FranTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home",
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToSearch = {
                    navController.navigate("search?dictionaryId=$it")
                }
            )
        }
        composable(
            "search?dictionaryId={id}",
            arguments = listOf(navArgument("id") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            })
        ) {
            SearchScreen(
                onNavigateToEntry = {
                    navController.navigate("entry/$it")
                }
            )
        }
        composable(
            "entry/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                })
        ) {
            DetailsScreen()
        }

    }
}