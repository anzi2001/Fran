package com.kockalabs.fran.ui.screens.home.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kockalabs.fran.models.Dictionary
import com.kockalabs.fran.ui.screens.home.HomeViewModel

@Composable
fun DictionaryList(
    onDictionaryClick: (Int) -> Unit,
    dictionaryList: List<Dictionary>
) {
    LazyRow{
        items(dictionaryList) {
            Dictionary(
                id = it.dictionaryId,
                image = it.imageUrl,
                title = it.name,
                onClick = onDictionaryClick
            )
        }
    }
}