package com.example.modul5.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modul5.data.model.Gunung
import com.example.modul5.data.repository.GunungRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GunungViewModel(private val repository: GunungRepository) : ViewModel() {

    private val _gunungList = MutableStateFlow<List<Gunung>>(emptyList())
    val gunungList: StateFlow<List<Gunung>> = _gunungList.asStateFlow()

    val favoriteList: StateFlow<List<Gunung>> = repository.getFavoriteGunungListFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedGunung = MutableStateFlow<Gunung?>(null)
    val selectedGunung: StateFlow<Gunung?> = _selectedGunung.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchGunungList()
    }

    private fun fetchGunungList() {
        viewModelScope.launch {
            try {
                val remoteList = repository.fetchGunungList()
                _gunungList.value = remoteList
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memuat data: ${e.localizedMessage}"
            }
        }
    }

    fun selectGunung(gunung: Gunung) {
        _selectedGunung.value = gunung
        Log.d("GunungViewModel", "Gunung dipilih: ${gunung.name}")
    }

    fun updateGunung(updatedGunung: Gunung) {
        viewModelScope.launch {
            try {
                if (updatedGunung.isFavorite) {
                    repository.insertFavoriteGunung(updatedGunung)
                } else {
                    repository.deleteFavoriteGunung(updatedGunung)
                }

                _gunungList.value = _gunungList.value.map {
                    if (it.name == updatedGunung.name) updatedGunung else it
                }

                Log.d("GunungViewModel", "Gunung diperbarui dan disimpan: ${updatedGunung.name}, Favorite: ${updatedGunung.isFavorite}")
            } catch (e: Exception) {
                _errorMessage.value = "Gagal menyimpan favorit: ${e.localizedMessage}"
            }
        }
    }

    fun onLinkClicked(gunung: Gunung) {
        Log.d("GunungViewModel", "Explicit intent diklik untuk: ${gunung.name}")
    }
}
