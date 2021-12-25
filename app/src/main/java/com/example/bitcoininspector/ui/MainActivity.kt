package com.example.bitcoininspector.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoininspector.MyApplication
import com.example.bitcoininspector.data.DTO.TransactionDTO
import com.example.bitcoininspector.databinding.ActivityMainBinding
import com.example.bitcoininspector.ui.adapter.TransactionsAdapter
import com.example.bitcoininspector.util.Socket.MessageListener
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MessageListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }

    private val serverUrl = "wss://ws.blockchain.info/inv"

    private lateinit var txns: ArrayList<TransactionDTO>
    private lateinit var transactionsAdapter: TransactionsAdapter
    private var exchangeRate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        txns = ArrayList()
        transactionsAdapter = TransactionsAdapter(txns)
        binding.transactions.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = transactionsAdapter
        }
        binding.clear.setOnClickListener {
            txns.clear()
            lifecycleScope.launch(Dispatchers.Main) {
                binding.transactions.recycledViewPool.clear();
                transactionsAdapter.notifyDataSetChanged()
            }
        }
        viewModel.webSocketListener = this
        viewModel.getExchangeRate()
        observeExchangeRate()
    }

    private fun observeExchangeRate() {
        viewModel.getExchangeRateLiveData.observe(this, {
            if(it!=null) {
                exchangeRate = it
                Log.d("ExchangeRate: ", exchangeRate.toString())
                viewModel.connectToSocket(serverUrl)
            }else{
                binding.status.text = "Error"
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeConnection()
    }

    override fun onConnectSuccess() {
        Log.d("SocketResponseStatus", "Connected")
        lifecycleScope.launch(Dispatchers.Main) {
            binding.status.text = "Connected"
        }
        viewModel.subscribeToUnconfirmedTransactions()
    }

    override fun onConnectFailed(error: String?) {
        Log.d("SocketResponseStatus", "Failed: " + error.toString())
        lifecycleScope.launch(Dispatchers.Main) {
            binding.status.text = "Disconnected"
        }
    }

    override fun onClose() {
        Log.d("SocketResponseStatus", "Close")
        lifecycleScope.launch(Dispatchers.Main) {
            binding.status.text = "Disconnected"
        }
    }

    override fun onMessage(text: String) {
        Log.d("SocketResponseStatus", "Received")
        Log.d("SocketResponse", text)
        val txn: TransactionDTO = Gson().fromJson(text, TransactionDTO::class.java)
        var satoshi: Long = 0
        for (input in txn.x?.inputs!!) {
            satoshi += input.prev_out?.value!!
        }
        val btc = satoshi * 0.00000001
        val usd = btc * exchangeRate
        if (usd > 100) {
            txn.amount = usd
            if (txns.size == 5) {
                txns.removeLast()
            }
            txns.add(0, txn)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.transactions.recycledViewPool.clear();
                transactionsAdapter.notifyDataSetChanged()
            }
        }
    }
}