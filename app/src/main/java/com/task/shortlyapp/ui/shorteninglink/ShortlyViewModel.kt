package com.task.shortlyapp.ui.shorteninglink

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.shortlyapp.repository.ShortlyAppRepository
import com.task.shortlyapp.repository.locale.entity.ShortlyLink
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

    val initialViewToggle = MutableLiveData(ViewType.ILLUSTRATION)
    val shorteningLinkState = MutableLiveData<NetworkState<*>>()

    fun initialViewToggle() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = shortlyAppRepository.getAllShortlyLinksCount()
            withContext(Dispatchers.Main) {
                initialViewToggle.value =
                    if (count == 0) ViewType.ILLUSTRATION else ViewType.DATA
            }
        }
    }

    fun getShortenLinksFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val links = shortlyAppRepository.getAllShortlyLinks()
            withContext(Dispatchers.Main) {
                shorteningLinkState.value = NetworkState.Success(links)
                initialViewToggle.value =
                    if (links.isEmpty()) ViewType.ILLUSTRATION else ViewType.DATA
            }
        }
    }

    fun deleteShortlyLink(shortlyLink: ShortlyLink) {
        viewModelScope.launch {
            shortlyAppRepository.deleteByCode(shortlyLink.code)
        }
    }

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
        shortlyAppRepository.addShortlyLink(shortenUrlResponse.result.apply {
            created_at = System.currentTimeMillis()
        })
        shorteningLinkState.value =
            NetworkState.Success(mutableListOf<ShortlyLink>().apply { add(shortenUrlResponse.result) })
        initialViewToggle.value = ViewType.DATA
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
