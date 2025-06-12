package com.example.modul5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modul5.data.repository.GunungRepository

class GunungViewModelFactory(private val repository: GunungRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GunungViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GunungViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}