package com.kockalabs.fran.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Dictionary(
    id: Int,
    image: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
    ) {
    Column(
        modifier = modifier
            .width(200.dp)
            .height(300.dp)
            .padding(16.dp)
            .clickable { onClick(id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://fran.si$image",
            contentDescription = title,
            modifier = Modifier.weight(0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, modifier = Modifier.weight(0.3f), textAlign = TextAlign.Center)
    }
}