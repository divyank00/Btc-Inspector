package com.example.bitcoininspector.data.DTO

class TransactionDTO {
    var op: String? = null
    var x: X? = null
    var amount: Double? = null
}

class X {
    var lock_time: String? = null
    var ver: String? = null
    var size: String? = null
    var inputs: List<Input>? = null
    var time: Long = 0
    var tx_index: String? = null
    var vin_sz: String? = null
    var hash: String? = null
    var vout_sz: String? = null
    var relayed_by: String? = null
    var out: List<Out>? = null
}


class Input {
    var sequence: String? = null
    var prev_out: PrevOut? = null
    var script: String? = null
}

class Out {
    var spent = false
    var tx_index: String? = null
    var type: String? = null
    var addr: String? = null
    var value: Long? = null
    var n: String? = null
    var script: String? = null
}

class PrevOut {
    var spent = false
    var tx_index: String? = null
    var type: String? = null
    var addr: String? = null
    var value: Long? = null
    var n: String? = null
    var script: String? = null
}
