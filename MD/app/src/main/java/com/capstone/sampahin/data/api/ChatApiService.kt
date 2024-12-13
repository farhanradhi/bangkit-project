package com.capstone.sampahin.data.api

import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.data.chat.ChatResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApiService {
    @GET("topics")
    suspend fun getTopics(): ChatRequest

    @GET("questions/{selectedTopic}")
    suspend fun getQuestions(
        @Path("selectedTopic")
        selectedTopic: String
    ): ChatRequest

    @POST("answer")
    suspend fun sendAnswer(
        @Body request: ChatRequest
    ): ChatResponse
}