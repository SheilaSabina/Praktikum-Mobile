package com.example.modul4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modul4.Gunung

class GunungViewModelFactory(private val gunungList: List<Gunung>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GunungViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GunungViewModel(gunungList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}