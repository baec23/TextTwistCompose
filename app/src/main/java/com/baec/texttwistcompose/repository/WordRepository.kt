package com.baec.texttwistcompose.repository

import com.baec.texttwistcompose.data.Word
import com.baec.texttwistcompose.util.Resource

interface WordRepository {
    suspend fun getRandomWord(wordLength: Int): Resource<Word>
}