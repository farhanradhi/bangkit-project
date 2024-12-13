package com.capstone.sampahin.data.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("history")
	val history: List<HistoryItem?>? = null
)

data class HistoryItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
