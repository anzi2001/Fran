package com.kockalabs.fran.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kockalabs.fran.ui.theme.Gray
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun FranSearchField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: ((String) -> Unit)? = null,
    onSearchClick: ((String) -> Unit)? = null,
    placeholder: String = "",
    readOnly: Boolean = false
) {
    val interactionSource = if (onSearchClick != null) {
        remember {
            object : MutableInteractionSource {
                override val interactions = MutableSharedFlow<Interaction>(
                    extraBufferCapacity = 16,
                    onBufferOverflow = BufferOverflow.DROP_OLDEST,
                )

                override suspend fun emit(interaction: Interaction) {
                    if (interaction is PressInteraction.Press) {
                        onSearchClick.invoke(value);
                    }

                    interactions.emit(interaction)
                }

                override fun tryEmit(interaction: Interaction): Boolean {
                    return interactions.tryEmit(interaction)
                }

            }
        }
    }
    else{
        remember{ MutableInteractionSource() }
    }


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange ?: { },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        readOnly = readOnly,
        interactionSource = interactionSource,
        suffix = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = null,
                modifier = Modifier.clickable { onSearchClick?.invoke(value) }
            )
        },
        placeholder = { Text(placeholder) },
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Gray,
            disabledSuffixColor = Gray,
            disabledTextColor = Gray
        )
    )
}