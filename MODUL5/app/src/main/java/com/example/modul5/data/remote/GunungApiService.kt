package com.example.modul5.data.remote

import com.example.modul5.data.model.Gunung
import retrofit2.http.GET

interface GunungApiService {
    @GET("gunung")
    suspend fun getGunungList(): List<Gunung>
}
