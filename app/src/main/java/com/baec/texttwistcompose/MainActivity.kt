package com.baec.texttwistcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baec.texttwistcompose.ui.gamescreen.GameScreen
import com.baec.texttwistcompose.ui.gamescreen.GameScreenViewModel
import com.baec.texttwistcompose.ui.main.MainScreen
import com.baec.texttwistcompose.ui.theme.TextTwistComposeTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TextTwistComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen"){
                        MainScreen(navController = navController)
                    }
                    composable("game_screen") {
                        Log.d(TAG, "onCreate: GameScreen called")
                        val gameScreenViewModel = hiltViewModel<GameScreenViewModel>()
                        GameScreen(
                            gameScreenViewModel = gameScreenViewModel,
                        )
                    }
                }
            }
        }
    }
}