package com.example.bitcoininspector.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoininspector.data.DTO.TransactionDTO
import com.example.bitcoininspector.databinding.TransactionItemBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionsAdapter(
    private val transactions: List<TransactionDTO>
) : RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsViewHolder {
        val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context))
        return TransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TransactionsViewHolder,
        position: Int
    ) = holder.bind(transactions[position])

    override fun getItemCount(): Int = transactions.size

    class TransactionsViewHolder(
        private val binding: TransactionItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(txn: TransactionDTO) {
            binding.hash.text = txn.x?.hash
            binding.amount.text = "$${txn.amount.toString()}"
            binding.time.text = convertToIST(txn)
        }

        private fun convertToIST(txn: TransactionDTO): String {
            val timestamp = txn.x?.time!!
            val timeD = Date(timestamp.times(1000))
            val sdf = SimpleDateFormat("dd-mm-yyyy hh:mm:ss")
            return sdf.format(timeD)
        }
    }
}