package com.baec.texttwistcompose.ui.gamescreen.solution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SolutionBox(
    word: String,
    isGuessed: Boolean,
    modifier: Modifier,
) {
    val guessed = remember { mutableStateOf(isGuessed) }
    val numChars = word.length
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(color = Color.LightGray)
            .wrapContentWidth(unbounded = false)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            for (i in 0 until numChars) {
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = Color.White)
                        .aspectRatio(1f)
                        .wrapContentHeight()
                )
                {
                    if (guessed.value) {
                        Text(
                            text = word[i].uppercase(),
                            fontSize = 24.sp,
                            modifier = modifier
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
    SolutionBox("TEST", true, Modifier)
}