package com.baec.texttwistcompose.ui.gamescreen

import com.baec.texttwistcompose.data.WordSolution

data class GameScreenState(
    val gameInProgress: Boolean = false,
    val solutions: List<WordSolution> = emptyList(),
    val guessedLetters: List<Char> = emptyList(),
    val availableLetters: List<Char> = emptyList(),
)