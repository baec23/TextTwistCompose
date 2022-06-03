package com.baec.texttwistcompose.ui.gamescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baec.texttwistcompose.TAG
import com.baec.texttwistcompose.data.Word
import com.baec.texttwistcompose.data.WordSolution
import com.baec.texttwistcompose.repository.WordRepository
import com.baec.texttwistcompose.ui.gamescreen.timer.TimerState
import com.baec.texttwistcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    var gameState: GameState by mutableStateOf(GameState())
        private set
    var timerState: TimerState by mutableStateOf(TimerState())
        private set
    var score by mutableStateOf(0)
        private set

    var totalAnswersCount: Int = 0
    var guessedAnswersCount: Int = 0

    init {
        loadGameData()
    }

    fun clickNewGame() {
        if(!gameState.isSixLetterGuessed)
            score = 0
        gameState.clear()
        loadGameData()
    }

    fun clickGuessedLetter(index: Int) {
        val letter = gameState.guessedLetters.removeAt(index)
        gameState.availableLetters.add(letter)
    }

    fun clickAvailableLetter(index: Int) {
        val letter = gameState.availableLetters.removeAt(index)
        gameState.guessedLetters.add(letter)
    }

    fun clickSubmit() {
        val sb = StringBuilder()
        for (c in gameState.guessedLetters)
            sb.append(c)
        checkAnswer(sb.toString())
    }

    fun clickTwist() {
        gameState.availableLetters.shuffle()
    }

    fun clickClear() {
        clearGuessedLetters()
    }

    fun clickShowAnswers() {
        revealAllAnswers()
        timerState.currentTime = 0L
    }

    fun onTimerFinished() {
        revealAllAnswers()
        clearGuessedLetters()
        gameState.isFinished = true
        gameState.isInProgress = false
        Log.d(TAG, "onTimerFinished: TIMER FINISHED")
    }

    private fun loadGameData() {
        viewModelScope.launch {
            val randomWordResult = wordRepository.getRandomWord(6)
            if (randomWordResult is Resource.Success) {
                initializeGameData(randomWordResult.data!!)
                gameState.isLoaded = true
                gameState.isInProgress = true
                timerState = TimerState()
                timerState.totalTime = 2 * 60 * 1000L
                timerState.currentTime = 2 * 60 * 1000L
                timerState.isActive = true
                totalAnswersCount = gameState.solutions.size
                guessedAnswersCount = 0
            }
        }
    }

    private fun checkAnswer(guessedWord: String) {
        for (wordSolution in gameState.solutions) {
            if (wordSolution.word == guessedWord) {
                guessedAnswersCount++
                score += guessedWord.length
                wordSolution.isGuessed.value = true
                if (guessedWord.length == 6)
                    gameState.isSixLetterGuessed = true
                if(guessedAnswersCount == totalAnswersCount) {
                    gameState.isFinished = true
                    gameState.isInProgress = false
                    timerState.currentTime = 0L
                }
                break
            }
        }
        clearGuessedLetters()
    }

    private fun revealAllAnswers() {
        for (wordSolution in gameState.solutions) {
            wordSolution.isRevealedByTimeout.value = true
        }
    }

    private fun initializeGameData(word: Word) {
        gameState.availableLetters.clear()
        gameState.guessedLetters.clear()
        gameState.solutions.clear()
        val shuffledWord = word.alphaWord.toCharArray()
        shuffledWord.shuffle()
        for (c in shuffledWord) {
            gameState.availableLetters.add(c)
        }
        for (solutionWord in word.solutions) {
            gameState.solutions.add(WordSolution(solutionWord, isGuessed = mutableStateOf(false)))
        }
    }

    private fun clearGuessedLetters() {
        val letters = gameState.guessedLetters.toList()
        gameState.guessedLetters.clear()
        gameState.availableLetters.addAll(letters)
    }
}
