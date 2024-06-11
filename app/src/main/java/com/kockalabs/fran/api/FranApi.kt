package com.kockalabs.fran.api

import com.kockalabs.fran.models.Dictionary
import com.kockalabs.fran.models.FranEntry
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FranApi {
    @GET("query")
    suspend fun query(
        @Query("term") term: String,
        @Query("dictionary") dictionaryId: Int?,
        @Query("page") page: Int?
    ): ApiResponse<List<FranEntry>>

    @GET("entry/{id}")
    suspend fun entry(@Path("id") id: Int): ApiResponse<FranEntry>

    @GET("dictionary")
    suspend fun dictionaries(): ApiResponse<List<Dictionary>>

    @GET("dictionary/{id}")
    suspend fun dictionary(@Path("id") id: Int): ApiResponse<Dictionary>

}