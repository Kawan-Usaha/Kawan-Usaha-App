package com.jetpack.kawanusaha.data

import com.google.gson.annotations.SerializedName

data class StreamResponse(
	@field:SerializedName("created")
	val created: Int,

	@field:SerializedName("model")
	val model: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("choices")
	val choices: List<ChoicesItem>,

	@field:SerializedName("object")
	val s: String
)

data class ChoicesItem(
	@field:SerializedName("finish_reason")
	val finishReason: String,

	@field:SerializedName("delta")
	val delta: Delta,

	@field:SerializedName("index")
	val index: Int
)


data class Event(
	val type: String,
	val data: String
)
