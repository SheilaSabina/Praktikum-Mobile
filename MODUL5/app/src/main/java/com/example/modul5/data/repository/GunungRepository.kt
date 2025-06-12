package com.example.modul5.data.repository

import android.content.Context
import com.example.modul5.data.local.database.AppDatabase
import com.example.modul5.data.mapper.toEntity
import com.example.modul5.data.mapper.toModel
import com.example.modul5.data.model.Gunung
import com.example.modul5.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GunungRepository(context: Context) {

    private val apiService = ApiClient.gunungApiService
    private val gunungDao = AppDatabase.getDatabase(context).gunungDao()

    fun getFavoriteGunungListFlow(): Flow<List<Gunung>> {
        return gunungDao.getAllFavoriteGunungFlow()
            .map { it.map { entity -> entity.toModel() } }
    }

    suspend fun fetchGunungList(): List<Gunung> = withContext(Dispatchers.IO) {
        try {
            val remoteList = apiService.getGunungList()
            val favoriteNames = gunungDao.getAllFavoriteGunung().map { it.name }

            val mergedList = remoteList.map { gunung ->
                if (gunung.name in favoriteNames) gunung.copy(isFavorite = true)
                else gunung
            }

            gunungDao.clearGunung()
            gunungDao.insertGunungList(mergedList.map { it.toEntity() })

            return@withContext mergedList
        } catch (e: Exception) {
            return@withContext gunungDao.getAllGunungOnce().map { it.toModel() }
        }
    }

    suspend fun updateGunung(gunung: Gunung) = withContext(Dispatchers.IO) {
        gunungDao.update(gunung.toEntity())
    }

    suspend fun insertFavoriteGunung(gunung: Gunung) = withContext(Dispatchers.IO) {
        gunungDao.updateFavoriteStatus(gunung.name, true)
    }

    suspend fun deleteFavoriteGunung(gunung: Gunung) = withContext(Dispatchers.IO) {
        gunungDao.updateFavoriteStatus(gunung.name, false)
    }
}