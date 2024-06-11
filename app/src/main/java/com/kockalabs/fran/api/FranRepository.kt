package com.kockalabs.fran.api

class FranRepository(private val franApi: FranApi) {

    suspend fun query(term: String, dictionaryId: Int? = null, page: Int) =
        franApi.query(term, dictionaryId, page)

    suspend fun entry(id: Int) = franApi.entry(id)

    suspend fun dictionaries() = franApi.dictionaries()

    suspend fun dictionary(id: Int) = franApi.dictionary(id)
}