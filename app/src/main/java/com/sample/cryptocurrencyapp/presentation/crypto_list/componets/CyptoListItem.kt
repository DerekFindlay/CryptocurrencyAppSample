package com.sample.cryptocurrencyapp.presentation.crypto_list.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sample.cryptocurrencyapp.domain.model.Crypto


@Composable
fun CryptoListItem(
    crypto: Crypto,
    onItemClick: (Crypto) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(crypto) }
            .padding(vertical = 1.dp)
            .background(
                if (crypto.rank % 2 == 0) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                }
            )
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${crypto.rank}. ${crypto.name} (${crypto.symbol}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Text(
            text = if (crypto.isActive) "Active" else "Inactive",
            color = if (crypto.isActive) MaterialTheme
                .colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.error,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(CenterVertically)
                .padding(horizontal = 20.dp)
        )
    }
}