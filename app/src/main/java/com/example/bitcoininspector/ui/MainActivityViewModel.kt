package com.example.bitcoininspector.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcoininspector.data.Repository.MainActivityRepository
import com.example.bitcoininspector.util.Socket.MessageListener
import com.example.bitcoininspector.util.Socket.WebSocketManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel
@Inject
constructor(
    private val mainActivityRepository: MainActivityRepository
) : ViewModel() {

    private val _getExchangeRateLiveData = MutableLiveData<Double?>()
    var getExchangeRateLiveData: LiveData<Double?> = _getExchangeRateLiveData

    var webSocketListener: MessageListener? = null

    fun connectToSocket(serverUrl: String) {
        viewModelScope.launch {
            webSocketListener?.let { WebSocketManager.init(serverUrl, it) }
            WebSocketManager.connect()
        }
    }

    fun subscribeToUnconfirmedTransactions() {
        viewModelScope.launch {
            WebSocketManager.sendMessage(
                "{\n" +
                        "  \"op\": \"unconfirmed_sub\"\n" +
                        "}"
            )
        }
    }

    fun unsubscribeToUnconfirmedTransactions() {
        viewModelScope.launch {
            WebSocketManager.sendMessage(
                "{\n" +
                        "  \"op\": \"unconfirmed_unsub\"\n" +
                        "}"
            )
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            WebSocketManager.close()
        }
    }

    fun getExchangeRate() {
        viewModelScope.launch {
            val response = mainActivityRepository.getExchangeRate()
            _getExchangeRateLiveData.postValue(response)
        }
    }
}
