package com.task.shortlyapp.utils

/**
 * Created by Muhammad Maqsood on 10/07/2022.
 */
sealed class NetworkState<out T> {

    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Failure<out T>(val error: T) : NetworkState<T>()
    data class Loading<out T>(val loading: T) : NetworkState<T>()
}
