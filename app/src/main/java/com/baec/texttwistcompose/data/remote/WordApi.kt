package com.baec.texttwistcompose.data.remote

import com.baec.texttwistcompose.data.remote.response.AlphaWordResponse
import com.baec.texttwistcompose.data.remote.response.WordSolutionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WordApi {

    @GET("/api/word/random")
    suspend fun getRandomWord(@Query("length") wordLength: Int): Response<AlphaWordResponse>

    @GET("/api/word/solution/{alphaWord}")
    suspend fun getSolutions(@Path("alphaWord")alphaWord: String): Response<WordSolutionResponse>
}