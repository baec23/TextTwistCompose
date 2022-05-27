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

        lifecycleScope.launchWhenCreated {
            val a = RetrofitInstance.api.getRandomWord(6)
            val response = try {
                RetrofitInstance.api.getRandomWord(6)
            } catch (e: IOException) {
                Log.d(TAG, "onCreate: IO EXCEPTION")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.d(TAG, "onCreate: HTTP EXCEPTION")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "RESPONSE RECEIVED ${response.body()}")
                val sol = RetrofitInstance.api.getSolutions(response.body()!!.alphaLetters)
                Log.d(TAG, "Solutions: ${sol.body()}")
            }
        }
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