package com.kockalabs.fran.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FranEntry(
    val id: Int,
    val word: String,
    val header: String,
    val dictionary: String,
    val definitions: List<FranDefinition>?
)