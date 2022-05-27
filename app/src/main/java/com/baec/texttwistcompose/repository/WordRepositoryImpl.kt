package com.baec.texttwistcompose.repository

import com.baec.texttwistcompose.data.Word
import com.baec.texttwistcompose.data.remote.WordApi
import com.baec.texttwistcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WordRepositoryImpl @Inject constructor(
    private val api: WordApi
) : WordRepository {

    override suspend fun getRandomWord(wordLength: Int): Resource<Word> {
        val response =
            try {
                api.getRandomWord(wordLength)
            } catch (e: Exception) {
                return Resource.Error("Error getting random alphaWord")
            }
        if (response.isSuccessful && response.body() != null) {
            val alphaWord = response.body()!!.alphaLetters
            val solutionsList = getSolutions(alphaWord)
            if (solutionsList is Resource.Success) {
                return Resource.Success(
                    Word(
                        alphaWord = alphaWord,
                        solutions = solutionsList.data!!
                    )
                )
            }
        }
        return Resource.Error("Unknown error occurred")
    }

    private suspend fun getSolutions(alphaWord: String): Resource<List<String>> {
        val solutionResponse =
            try {
                api.getSolutions(alphaWord)
            } catch (e: Exception) {
                return Resource.Error("Error getting solutions")
            }
        if (solutionResponse.isSuccessful && solutionResponse.body() != null) {
            val solutionList = mutableListOf<String>()
            for (solutionItem in solutionResponse.body()!!) {
                solutionList.add(solutionItem.word)
            }
            return Resource.Success(solutionList)
        }
        return Resource.Error("Error getting solutions")
    }
}