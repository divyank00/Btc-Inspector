package com.example.bitcoininspector.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.Socket

class WebSocket {
    suspend fun subscribeToUnconfirmedTransactions() {
        withContext(Dispatchers.IO) {
            try {
                val socket = Socket("wss://ws.blockchain.info/inv", 9100)
                val outputStream = socket.getOutputStream()

                outputStream.use {
                    it.write("xxx".toByteArray())
                }
            } catch (e: IOException) {
                //log your error
                Log.d("Error",e.message.toString())
            }
        }
    }
}