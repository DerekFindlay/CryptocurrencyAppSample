package com.sample.cryptocurrencyapp.presentation.theme.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sample.cryptocurrencyapp.R
import java.lang.reflect.Modifier

@Composable
fun AppBar(){
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),

            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}