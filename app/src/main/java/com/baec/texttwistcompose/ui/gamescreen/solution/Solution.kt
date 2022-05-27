package com.baec.texttwistcompose.ui.gamescreen.solution

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SolutionBox(
    word: String,
    isGuessed: Boolean,
    modifier: Modifier,
) {
    val guessed = remember { mutableStateOf(isGuessed) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
    ) {
        if(guessed.value) {
            Text(
                text = word,
                fontSize = 24.sp,
                modifier = modifier
            )
        }
    }
}