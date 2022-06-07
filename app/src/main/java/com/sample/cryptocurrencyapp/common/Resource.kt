package com.sample.cryptocurrencyapp.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}


//Practice this to understand how it works. I suggest a lot of play with kotlin generics to get this
//concept down fully.
sealed class MyResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : MyResource<T>(data)
    class Error<T>(data: T? = null, message: String) : MyResource<T>(data, message)
    class Loading<T>(data: T? = null) : MyResource<T>(data)
}



















