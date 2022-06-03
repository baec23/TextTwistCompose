package com.baec.texttwistcompose.ui.gamescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.baec.texttwistcompose.data.WordSolution

class GameState {
    var isLoaded by mutableStateOf(false)
    var isInProgress by mutableStateOf(false)
    var isFinished by mutableStateOf(false)
    var isSixLetterGuessed by mutableStateOf(false)
    val guessedLetters = mutableStateListOf<Char>()
    val availableLetters = mutableStateListOf<Char>()
    val solutions = mutableStateListOf<WordSolution>()
    fun clear(){
        isLoaded = false
        isInProgress = false
        isFinished = false
        isSixLetterGuessed = false
        guessedLetters.clear()
        availableLetters.clear()
        solutions.clear()
    }
}