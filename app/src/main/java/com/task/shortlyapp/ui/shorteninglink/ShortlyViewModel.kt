package com.task.shortlyapp.ui.shorteninglink

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.shortlyapp.repository.ShortlyAppRepository
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
    shortlyApp: Application,
    private val shortlyAppRepository: ShortlyAppRepository
) :
    AndroidViewModel(shortlyApp) {

    val shorteningLinkState = MutableLiveData<NetworkState<*>>()

    fun shortenLink(link: String) {
        viewModelScope.launch {
            shorteningLinkState.value = NetworkState.Loading(true)
            try {
                val response = withContext(Dispatchers.Default) {
                    shortlyAppRepository.shortenUrl(url = link)
                }
                shorteningLinkState.value = NetworkState.Success(response)
            } catch (e: HttpException) {
                e.response().takeIf { it?.errorBody() != null }?.let {
                    shorteningLinkState.value =
                        NetworkState.Failure(parseShortyLinkErrorResponse(it.errorBody())?.error)
                } ?: kotlin.run {
                    shorteningLinkState.value = NetworkState.Failure(e.localizedMessage)
                }
            }
        }
    }
}
