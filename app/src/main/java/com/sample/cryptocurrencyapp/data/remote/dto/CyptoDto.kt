package com.sample.cryptocurrencyapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.sample.cryptocurrencyapp.domain.model.Crypto

data class CyptoDto(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

fun CyptoDto.toCrypto(): Crypto {
    return Crypto(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}