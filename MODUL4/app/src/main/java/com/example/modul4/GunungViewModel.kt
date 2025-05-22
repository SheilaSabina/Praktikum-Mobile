package com.example.modul4.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modul4.Gunung
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GunungViewModel(private val initialList: List<Gunung>) : ViewModel() {

    private val _gunungList = MutableStateFlow<List<Gunung>>(emptyList())
    val gunungList: StateFlow<List<Gunung>> get() = _gunungList

    private val _selectedGunung = MutableStateFlow<Gunung?>(null)
    val selectedGunung: StateFlow<Gunung?> get() = _selectedGunung

    init {
        viewModelScope.launch {
            _gunungList.value = initialList
            Log.d("GunungViewModel", "Data gunung dimasukkan: ${initialList.size} item")
        }
    }

    fun onDetailClicked(gunung: Gunung) {
        _selectedGunung.value = gunung
        Log.d("GunungViewModel", "Detail diklik: ${gunung.name}")
    }

    fun onLinkClicked(gunung: Gunung) {
        Log.d("GunungViewModel", "Explicit intent diklik untuk: ${gunung.name}")
    }
}