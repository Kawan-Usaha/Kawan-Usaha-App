//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[getUsahaDetail](get-usaha-detail.md)

# getUsahaDetail

[androidJvm]\

@GET(value = &quot;usaha/detail&quot;)

abstract fun [getUsahaDetail](get-usaha-detail.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = &quot;id&quot;)id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Call&lt;[UsahaDetailResponse](../../com.jetpack.kawanusaha.data/-usaha-detail-response/index.md)&gt;
