package com.task.shortlyapp.ui.shorteninglink

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.shortlyapp.repository.ShortlyAppRepository
import com.task.shortlyapp.repository.models.ShortenUrlResponse
import com.task.shortlyapp.utils.NetworkState
import com.task.shortlyapp.utils.parseShortyLinkErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@HiltViewModel
class ShortlyViewModel @Inject constructor(
    private val shortlyAppRepository: ShortlyAppRepository
) :
    ViewModel() {

    val shorteningLinkState = MutableLiveData<NetworkState<*>>()

    fun shortenLink(link: String) {
        viewModelScope.launch {
            shorteningLinkState.value = NetworkState.Loading(true)
            try {
                val response = withContext(Dispatchers.Default) {
                    shortlyAppRepository.shortenUrl(url = link)
                }
                onShortlySuccess(shortenUrlResponse = response)
            } catch (e: HttpException) {
                onShortlyError(httpException = e)
            }
        }
    }

    private suspend fun onShortlySuccess(shortenUrlResponse: ShortenUrlResponse) {
        shortlyAppRepository.addShortlyLink(shortenUrlResponse.result)
        shorteningLinkState.value = NetworkState.Success(shortenUrlResponse)
    }

    private fun onShortlyError(httpException: HttpException) {
        httpException.response().takeIf { it?.errorBody() != null }?.let {
            shorteningLinkState.value =
                NetworkState.Failure(parseShortyLinkErrorResponse(it.errorBody())?.error)
        } ?: kotlin.run {
            shorteningLinkState.value = NetworkState.Failure(httpException.localizedMessage)
        }
    }
}
