package com.kockalabs.fran.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsScreen(viewModel: DetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    if (uiState.loading) {
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if(uiState.error != null){
        Text(uiState.error!!)
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                uiState.entry?.word ?: "",
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                uiState.entry?.header ?: "",
                modifier = Modifier.padding(bottom = 2.dp),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(24.dp)
        ){
            Column(
                modifier = Modifier.padding(8.dp)
            ){
                for (definition in uiState.entry?.definitions ?: emptyList()) {
                    Text("\u2022 ${definition.definition}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }

}