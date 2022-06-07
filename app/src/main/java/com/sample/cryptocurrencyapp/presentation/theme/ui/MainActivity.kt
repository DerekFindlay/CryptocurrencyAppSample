package com.sample.cryptocurrencyapp.presentation.theme.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.cryptocurrencyapp.presentation.Screen
import com.sample.cryptocurrencyapp.presentation.crypto_detail.CryptoDetailScreen
import com.sample.cryptocurrencyapp.presentation.crypto_list.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptocurrencyAppYTTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    Scaffold(
                        topBar = {
                            AppBar()
                        }
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.CryptoListScreen.routes
                        ) {
                            composable(
                                route = Screen.CryptoListScreen.routes
                            ) {
                                CryptoListScreen(navController)
                            }
                            composable(
                                route = Screen.CryptoDetailScreen.routes + "/{coinId}"
                            ) {
                                CryptoDetailScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

