package com.kockalabs.fran.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FranDefinition(
    val definition: String,
    val examples: List<String>?,
    val subDefinitions: List<FranDefinition>?
)
