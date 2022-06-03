package com.baec.texttwistcompose.ui.gamescreen.solution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baec.texttwistcompose.data.WordSolution
import com.baec.texttwistcompose.ui.theme.LightGrey
import com.baec.texttwistcompose.ui.theme.LightRed

@Composable
fun SolutionList(
    solutionStateList: List<WordSolution>,
    modifier: Modifier,
) {
    val solutions3 = mutableListOf<WordSolution>()
    val solutions4 = mutableListOf<WordSolution>()
    val solutions5 = mutableListOf<WordSolution>()
    val solutions6 = mutableListOf<WordSolution>()
    for (solution in solutionStateList) {
        when (solution.word.length) {
            3 -> solutions3.add(solution)
            4 -> solutions4.add(solution)
            5 -> solutions5.add(solution)
            6 -> solutions6.add(solution)
        }
    }
    val orderedSolutions = mutableListOf<WordSolution>()
    solutions3.forEach { orderedSolutions.add(it) }
    solutions4.forEach { orderedSolutions.add(it) }
    solutions5.forEach { orderedSolutions.add(it) }
    solutions6.forEach { orderedSolutions.add(it) }

    var numColumns = solutionStateList.size / 8
    val remainder = solutionStateList.size % 8
    if (remainder != 0) numColumns++
    var index = 0
    for (i in 0 until numColumns) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            for (j in 0 until 8) {
                if (index >= orderedSolutions.size)
                    break
                SolutionBox(
                    word = orderedSolutions[index].word,
                    isGuessed = orderedSolutions[index].isGuessed.value,
                    isRevealedByTimeout = orderedSolutions[index].isRevealedByTimeout.value,
                    modifier = modifier
                )
                index++
            }
        }
        Spacer(modifier = Modifier.width(25.dp))
    }
}

@Composable
fun SolutionBox(
    word: String,
    isGuessed: Boolean,
    isRevealedByTimeout: Boolean,
    modifier: Modifier,
) {
    val numChars = word.length
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = LightGrey)
            .padding(5.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            for (i in 0 until numChars) {
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = if (isRevealedByTimeout && !isGuessed) LightRed else Color.White)
                        .width(30.dp)
                        .height(30.dp)
                )
                {
                    if (isGuessed || isRevealedByTimeout) {
                        Text(
                            text = word[i].uppercase(),
                            fontSize = 24.sp,
                            modifier = modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    SolutionBox("TEST", false, true, Modifier)
}