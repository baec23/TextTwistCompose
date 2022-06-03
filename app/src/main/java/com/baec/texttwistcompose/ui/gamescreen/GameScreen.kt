package com.baec.texttwistcompose.ui.gamescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baec.texttwistcompose.ui.gamescreen.solution.SolutionList
import com.baec.texttwistcompose.ui.gamescreen.timer.Timer
import com.baec.texttwistcompose.ui.theme.DarkBlue
import com.baec.texttwistcompose.ui.theme.LightGrey

@Composable
fun GameScreen(
    gameScreenViewModel: GameScreenViewModel,
) {
    val scrollState = rememberScrollState()
    val gameState = gameScreenViewModel.gameState
    Box() {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                ScoreBox(score = gameScreenViewModel.score, modifier = Modifier)
                Timer(
                    timerState = gameScreenViewModel.timerState,
                    inactiveBarColor = LightGrey,
                    activeBarColor = DarkBlue,
                    modifier = Modifier.size(100.dp),
                    isTimerRunning = gameState.isInProgress,
                    onTimerFinished = gameScreenViewModel::onTimerFinished
                )
            }

            Row() {
                GuessedLetters(
                    gameState.guessedLetters,
                    modifier = Modifier,
                    onClick = gameScreenViewModel::clickGuessedLetter
                )
            }
            Row() {
                AvailableLetters(
                    gameState.availableLetters,
                    modifier = Modifier,
                    onClick = gameScreenViewModel::clickAvailableLetter
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = gameScreenViewModel::clickSubmit,
                    enabled = gameState.isInProgress,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        disabledElevation = 0.dp,
                        pressedElevation = 2.dp,
                    )
                ) {
                    Text(text = "Enter")
                }
                Button(
                    onClick = gameScreenViewModel::clickClear,
                    enabled = gameState.isInProgress,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        disabledElevation = 0.dp,
                        pressedElevation = 2.dp,
                    )
                ) {
                    Text(text = "Clear")
                }
                Button(
                    onClick = gameScreenViewModel::clickTwist,
                    enabled = gameState.isInProgress,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        disabledElevation = 0.dp,
                        pressedElevation = 2.dp,
                    )
                ) {
                    Text(text = "Twist")
                }
                Button(
                    onClick = gameScreenViewModel::clickShowAnswers,
                    enabled = gameState.isInProgress,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        disabledElevation = 0.dp,
                        pressedElevation = 2.dp,
                    )
                ) {
                    Text(text = "Show Answers")
                }
                Button(
                    onClick = gameScreenViewModel::clickNewGame,
                    enabled = gameState.isSixLetterGuessed || !gameState.isInProgress,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 5.dp,
                        disabledElevation = 0.dp,
                        pressedElevation = 2.dp,
                    )
                ) {
                    Text(text = "New Game")
                }
            }
            Row() {
                SolutionList(
                    solutionStateList = gameState.solutions,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ScoreBox(
    score: Int,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .wrapContentSize()
            .then(modifier)
    )
    {
        Row(
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "Score: $score",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun GuessedLetters(
    guessedLetters: List<Char>,
    modifier: Modifier,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(100.dp)
            .then(modifier)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (i in guessedLetters.indices) {
                GameLetter(
                    letter = guessedLetters[i],
                    modifier = modifier,
                    index = i,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun AvailableLetters(
    availableLetters: List<Char>,
    modifier: Modifier,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .height(100.dp)
            .then(modifier)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in availableLetters.indices) {
                GameLetter(
                    letter = availableLetters[i],
                    modifier = modifier,
                    index = i,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun GameLetter(
    letter: Char,
    modifier: Modifier,
    index: Int,
    onClick: (Int) -> Unit,
    fontSize: TextUnit = 48.sp,
) {
    @Suppress("NAME_SHADOWING") val letter = letter.uppercaseChar()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(color = LightGrey)
            .clickable { onClick(index) }
            .then(modifier)
    ) {
        Text(
            text = letter.toString(),
            fontSize = fontSize,
            color = DarkBlue,
            modifier = modifier
        )
    }
}