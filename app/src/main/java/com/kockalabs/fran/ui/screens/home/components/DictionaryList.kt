package com.kockalabs.fran.ui.screens.home.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kockalabs.fran.ui.screens.home.HomeViewModel

@Composable
fun DictionaryList(homeViewModel: HomeViewModel = hiltViewModel(), onDictionaryClick: (Int) -> Unit) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val currentOnDictionaryClick by rememberUpdatedState(onDictionaryClick)
    LazyRow{
        items(uiState.dictionaryList) {
            Dictionary(
                id = it.dictionaryId,
                image = it.imageUrl,
                title = it.name,
                onClick = currentOnDictionaryClick
            )
        }
    }
}