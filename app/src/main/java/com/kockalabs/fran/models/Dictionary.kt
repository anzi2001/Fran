package com.kockalabs.fran.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Dictionary(
    val dictionaryId: Int,
    val name: String,
    val imageUrl: String,
    val url: String
)