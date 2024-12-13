package com.capstone.sampahin.data.maps

import com.google.gson.annotations.SerializedName

data class MapsResponses(

	@field:SerializedName("MapsResponses")
	val mapsResponses: List<MapsResponsesItem?>? = null
)

data class MapsResponsesItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)
