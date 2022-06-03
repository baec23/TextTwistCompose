package com.baec.texttwistcompose.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class WordSolution(
    val word: String,
    var isGuessed: MutableState<Boolean> = mutableStateOf(false),
    var isRevealedByTimeout: MutableState<Boolean> = mutableStateOf(false),
)
