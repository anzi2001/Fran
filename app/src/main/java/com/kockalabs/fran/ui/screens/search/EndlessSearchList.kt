package com.kockalabs.fran.ui.screens.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kockalabs.fran.models.FranEntry

const val NEXT_PAGE_THRESHOLD = 2

@Composable
fun EndlessSearchList(
    modifier: Modifier = Modifier,
    items: List<FranEntry>,
    loading: Boolean,
    onItemClicked: (FranEntry) -> Unit,
    onBottomReached: () -> Unit
) {
    val listState = rememberLazyListState()
    val updatedBottomReached by rememberUpdatedState(onBottomReached)

    val isBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index != 0 && lastVisibleItem.index  >= listState.layoutInfo.totalItemsCount - NEXT_PAGE_THRESHOLD
        }
    }

    LaunchedEffect(isBottom) {
        if (isBottom) {
            updatedBottomReached()
        }
    }

    LazyColumn(state = listState, modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(items) {
            SearchItem(
                franEntry = it,
                onClick = { onItemClicked(it) }
            )
        }
        item{
            AnimatedVisibility(visible = loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}