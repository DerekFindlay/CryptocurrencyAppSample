package com.sample.cryptocurrencyapp.presentation.crypto_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.sample.cryptocurrencyapp.presentation.crypto_detail.componets.CryptoDetailViewModel
import com.sample.cryptocurrencyapp.presentation.crypto_detail.componets.CryptoTag
import com.sample.cryptocurrencyapp.presentation.crypto_detail.componets.TeamListItem


@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 65.dp)
    ) {
        state.crypto?.let { cryptoDetail ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${cryptoDetail.rank}. ${cryptoDetail.name} (${cryptoDetail.symbol})",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(8f)
                        )
                        Text(
                            text = if (cryptoDetail.isActive) "Active" else "Inactive",
                            color = if (cryptoDetail.isActive) MaterialTheme
                                .colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.error,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .align(CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = cryptoDetail.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    //The flowRow will wrap items below if they do not fit within the row.
                    FlowRow(
                        mainAxisSpacing = 10.dp,
                        crossAxisSpacing = 20.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        cryptoDetail.tags.forEach { tag ->
                            CryptoTag(tag = tag)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Team Members",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                }
                items(cryptoDetail.team) { teamMember ->
                    TeamListItem(
                        teamMember = teamMember,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Divider()
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}