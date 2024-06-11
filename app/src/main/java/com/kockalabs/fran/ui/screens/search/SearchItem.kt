package com.kockalabs.fran.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kockalabs.fran.models.FranEntry

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    franEntry: FranEntry,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(franEntry.word, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    franEntry.header,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1.0f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                        .padding(12.dp, 2.dp)
                ) {
                    Text(
                        franEntry.dictionary,
                        softWrap = false,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
            if (franEntry.definitions?.isNotEmpty() == true) {
                Text(franEntry.definitions.first().definition)
            }
        }
    }
}