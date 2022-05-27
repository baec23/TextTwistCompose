package com.baec.texttwistcompose.ui

import GameScreenViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.baec.texttwistcompose.ui.gamescreen.GameScreenState
import com.baec.texttwistcompose.ui.gamescreen.solution.SolutionBox
import com.baec.texttwistcompose.ui.theme.DarkBlue
import com.baec.texttwistcompose.ui.theme.LightGrey

@Composable
fun GameScreen(
    navController: NavController,
    gameScreenViewModel: GameScreenViewModel,
) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row() {
                SolutionBox(word = "TEST", isGuessed = true, modifier = Modifier)
            }
            Row() {
                GuessedLetters(
                    gameScreenViewModel.gameState,
                    modifier = Modifier,
                    onClick = gameScreenViewModel::clickGuessedLetter
                )
            }
            Row() {
                AvailableLetters(
                    gameScreenViewModel.gameState,
                    modifier = Modifier,
                    onClick = gameScreenViewModel::clickAvailableLetter
                )
            }
        }
    }
}

@Composable
fun GuessedLetters(
    gameState: GameScreenState,
    modifier: Modifier,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .then(modifier)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (i in gameState.guessedLetters.indices) {
                GameLetter(
                    letter = gameState.guessedLetters[i],
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
    gameState: GameScreenState,
    modifier: Modifier,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .then(modifier)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in gameState.availableLetters.indices) {
                GameLetter(
                    letter = gameState.availableLetters[i],
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
    size: Dp = 50.dp,
    fontSize: TextUnit = 24.sp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
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