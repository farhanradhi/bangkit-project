package com.capstone.sampahin.data.chat

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @field: SerializedName("answers")
    val answers: List<String>? = emptyList()
)

