package com.baec.texttwistcompose.data.remote.response

data class WordSolutionItemResponse(
    val id: Int,
    val length: Int,
    val parentAlpha: String,
    val word: String
)