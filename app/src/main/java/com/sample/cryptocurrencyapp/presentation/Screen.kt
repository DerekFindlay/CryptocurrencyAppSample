package com.sample.cryptocurrencyapp.presentation

sealed class Screen(val routes: String) {
    object CryptoListScreen: Screen("coin_list_screen")
    object CryptoDetailScreen: Screen("coin_detail_screen")
}
