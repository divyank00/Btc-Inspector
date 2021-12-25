package com.example.bitcoininspector.util.Socket

interface MessageListener {
    fun  onConnectSuccess ()
    fun  onConnectFailed (error: String?)
    fun  onClose ()
    fun onMessage(text: String)
}