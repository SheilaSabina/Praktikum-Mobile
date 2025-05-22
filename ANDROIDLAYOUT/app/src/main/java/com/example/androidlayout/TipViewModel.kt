package com.example.androidlayout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

class TipViewModel : ViewModel() {

    private val _biayaInput = MutableLiveData<String>()
    val biayaInput: LiveData<String> = _biayaInput

    private val _tipResult = MutableLiveData<String>()
    val tipResult: LiveData<String> = _tipResult

    private val _bulatkan = MutableLiveData<Boolean>()
    val bulatkan: LiveData<Boolean> = _bulatkan

    private var persenTip: Double = 0.0

    fun setBiaya(biaya: String) {
        _biayaInput.value = biaya
    }

    fun setBulatkan(value: Boolean) {
        _bulatkan.value = value
    }

    fun setPersenTip(value: Double) {
        persenTip = value
    }

    fun hitungTip() {
        val biaya = _biayaInput.value?.toDoubleOrNull()
        if (biaya == null || biaya <= 0 || persenTip == 0.0) {
            _tipResult.value = ""
            return
        }

        var tip = biaya * persenTip
        if (_bulatkan.value == true) {
            tip = ceil(tip)
        }

        _tipResult.value = "Jumlah tip: Rp ${"%.2f".format(tip)}"
    }
}