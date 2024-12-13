package com.capstone.sampahin.data.predict

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null
)
