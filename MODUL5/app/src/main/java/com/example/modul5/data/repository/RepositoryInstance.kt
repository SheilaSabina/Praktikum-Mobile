package com.example.modul5.data.repository

import android.content.Context

object RepositoryInstance {
    private var repository: GunungRepository? = null

    fun provideRepository(context: Context): GunungRepository {
        return repository ?: synchronized(this) {
            val instance = GunungRepository(context.applicationContext)
            repository = instance
            instance
        }
    }
}