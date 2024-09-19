package com.kockalabs.fran.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kockalabs.fran.R
import com.kockalabs.fran.ui.common.FranSearchField
import com.kockalabs.fran.ui.screens.home.components.DictionaryList

@Composable
fun HomeScreen(
    onNavigateToSearch: (Int?) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        FranSearchField(
            value = "",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            readOnly = true,
            placeholder = stringResource(R.string.search_hint),
            onSearchClick = { onNavigateToSearch(null) }
        )
        DictionaryList(
            dictionaryList = uiState.dictionaryList,
            onDictionaryClick = { onNavigateToSearch(it) }
        )
    }
}