package com.kockalabs.fran.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kockalabs.fran.api.FranRepository
import com.kockalabs.fran.models.FranEntry
import com.kockalabs.fran.utils.WhileUiSubscribed
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val franRepository: FranRepository, savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val entryId = savedStateHandle.get<Int>("id")
    private val entry = MutableStateFlow<FranEntry?>(null)
    private val error = MutableStateFlow<String?>(null);
    private val loading = MutableStateFlow(true)

    init {
        entryId?.let {
            viewModelScope.launch {
                loading.value = true
                when (val result = franRepository.entry(it)) {
                    is ApiResponse.Success -> entry.value = result.data
                    is ApiResponse.Failure -> error.value = result.message()
                }
                loading.value = false
            }
        }
    }

    val state: StateFlow<DetailsViewModelState> = combine(
        entry, loading, error
    ) { entry, loading, error ->
        DetailsViewModelState(
            entryId = entryId,
            entry = entry,
            loading = loading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = DetailsViewModelState()
    )
}

data class DetailsViewModelState(
    val entryId: Int? = null,
    val entry: FranEntry? = null,
    val loading: Boolean = false,
    val error: String? = null
)