package com.baec.texttwistcompose

import GameScreenViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baec.texttwistcompose.ui.GameScreen
import com.baec.texttwistcompose.ui.theme.TextTwistComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TextTwistComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "game_screen") {
                    composable("game_screen"){ GameScreen(
                        navController = navController,
                        gameScreenViewModel = viewModel<GameScreenViewModel>()
                    )}
                }
            }
        }
    }
}