package com.baec.texttwistcompose.repository

import com.baec.texttwistcompose.data.Word
import com.baec.texttwistcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
interface WordRepository {
    suspend fun getRandomWord(wordLength: Int): Resource<Word>
}