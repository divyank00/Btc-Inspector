package com.example.bitcoininspector.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcoininspector.util.WebSocket
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel
@Inject
constructor(
    private val webSocket: WebSocket
) : ViewModel() {
    fun connectToSocket() {
        viewModelScope.launch {
            webSocket.subscribeToUnconfirmedTransactions()
        }
    }
}
