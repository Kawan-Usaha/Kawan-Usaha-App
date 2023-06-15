package com.jetpack.kawanusaha.data

import com.google.gson.annotations.SerializedName


data class InternetSearchResponse(

	@field:SerializedName("web")
	val web: Web,

	@field:SerializedName("query")
	val query: Query,

	@field:SerializedName("mixed")
	val mixed: Mixed,

	@field:SerializedName("type")
	val type: String
)

data class Web(

	@field:SerializedName("family_friendly")
	val familyFriendly: Boolean,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("results")
	val results: List<ResultsItem>
)

data class Mixed(

	@field:SerializedName("side")
	val side: List<Any>,

	@field:SerializedName("top")
	val top: List<Any>,

	@field:SerializedName("main")
	val main: List<MainItem>,

	@field:SerializedName("type")
	val type: String
)

data class Query(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("original")
	val original: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("more_results_available")
	val moreResultsAvailable: Boolean,

	@field:SerializedName("bad_results")
	val badResults: Boolean,

	@field:SerializedName("header_country")
	val headerCountry: String,

	@field:SerializedName("should_fallback")
	val shouldFallback: Boolean,

	@field:SerializedName("spellcheck_off")
	val spellcheckOff: Boolean,

	@field:SerializedName("is_news_breaking")
	val isNewsBreaking: Boolean,

	@field:SerializedName("state")
	val state: String,

	@field:SerializedName("postal_code")
	val postalCode: String,

	@field:SerializedName("show_strict_warning")
	val showStrictWarning: Boolean,

	@field:SerializedName("is_navigational")
	val isNavigational: Boolean
)

data class MainItem(

	@field:SerializedName("all")
	val all: Boolean,

	@field:SerializedName("index")
	val index: Int,

	@field:SerializedName("type")
	val type: String
)

data class ResultsItem(

	@field:SerializedName("is_source_local")
	val isSourceLocal: Boolean,

	@field:SerializedName("subtype")
	val subtype: String,

	@field:SerializedName("family_friendly")
	val familyFriendly: Boolean,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("language")
	val language: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("is_source_both")
	val isSourceBoth: Boolean,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("meta_url")
	val metaUrl: MetaUrl,

	@field:SerializedName("age")
	val age: String,

	@field:SerializedName("extra_snippets")
	val extraSnippets: List<String>?
)

data class MetaUrl(

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("hostname")
	val hostname: String,

	@field:SerializedName("scheme")
	val scheme: String,

	@field:SerializedName("favicon")
	val favicon: String,

	@field:SerializedName("netloc")
	val netloc: String
)
