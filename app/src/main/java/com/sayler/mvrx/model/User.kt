package com.sayler.mvrx.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Long,
    val name: String,
    val username: String
)
