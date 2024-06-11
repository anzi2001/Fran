package com.kockalabs.fran.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kockalabs.fran.api.FranRepository
import com.kockalabs.fran.models.Dictionary
import com.kockalabs.fran.models.FranEntry
import com.kockalabs.fran.utils.WhileUiSubscribed
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val franRepository: FranRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var searchJob : Job? = null
    private var page = 1

    private val dictionaryId = savedStateHandle.get<String>("dictionaryId")?.toInt()
    private val dictionary = MutableStateFlow<Dictionary?>(null)
    private val isLoading = MutableStateFlow(false)
    private val searchResults = MutableStateFlow(emptyList<FranEntry>())
    private val searchQuery = MutableStateFlow("")
    private val error = MutableStateFlow("")

    init{
        dictionaryId?.let {
            viewModelScope.launch {
                isLoading.value = true
                when(val result = franRepository.dictionary(it)){
                    is ApiResponse.Success -> dictionary.value = result.data
                    is ApiResponse.Failure -> error.value = result.message()
                }
                isLoading.value = false
            }
        }
    }

    val state = combine(
        isLoading, searchResults, error, dictionary, searchQuery
    ){loading, results, error, dictionary, searchQuery ->
        SearchViewModelState(
            dictionaryId = dictionaryId,
            searchQuery = searchQuery,
            searchResults = results,
            dictionary = dictionary,
            loading = loading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = SearchViewModelState()
    )

    fun search(term: String){
        if(term.isEmpty()) return
        searchQuery.value = term
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            isLoading.value = true
            delay(300L)
            when(val results = franRepository.query(term, dictionaryId, page)){
                is ApiResponse.Success -> searchResults.value = results.data
                is ApiResponse.Failure -> error.value = results.message()
            }
            isLoading.value = false
        }
    }

    fun loadNextPage(){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            isLoading.value = true
            val currentResults = searchResults.value
            page++
            when(val results = franRepository.query(searchQuery.value, dictionaryId, page)){
                is ApiResponse.Success -> searchResults.value = currentResults + results.data
                is ApiResponse.Failure -> error.value = results.message()
            }
            isLoading.value = false
        }
    }
}

data class SearchViewModelState(
    val dictionaryId: Int? = null,
    val searchResults: List<FranEntry> = emptyList(),
    val searchQuery: String = "",
    val dictionary: Dictionary? = null,
    val loading: Boolean = false,
    val error: String = ""
)