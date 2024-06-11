package com.kockalabs.fran.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kockalabs.fran.api.FranRepository
import com.kockalabs.fran.models.Dictionary
import com.kockalabs.fran.utils.WhileUiSubscribed
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.*

@HiltViewModel
class HomeViewModel @Inject constructor(private val franRepository: FranRepository) : ViewModel() {

    private val dictionaryList = MutableStateFlow(emptyList<Dictionary>())
    private val isLoading = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)

    val uiState = combine(
        isLoading, error, dictionaryList
    ) { isLoading, error, dictionaryList ->
        HomeState(
            isLoading = isLoading,
            error = error,
            dictionaryList = dictionaryList
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = HomeState()
    )

    init{
        getDictionaryList()
    }

    private fun getDictionaryList(){
        viewModelScope.launch {
            when(val dictResponse = franRepository.dictionaries()){
                is ApiResponse.Success -> dictionaryList.value = dictResponse.data
                is ApiResponse.Failure -> error.value = dictResponse.message()
            }
        }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val dictionaryList : List<Dictionary> = emptyList()
)