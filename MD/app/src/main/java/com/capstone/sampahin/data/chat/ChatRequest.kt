package com.capstone.sampahin.data.chat

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @field:SerializedName("topics")
    val topics: List<String> = emptyList(),

    @field: SerializedName("questions")
    val questions: List<String> = emptyList()
)