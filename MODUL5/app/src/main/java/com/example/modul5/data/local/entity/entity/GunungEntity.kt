package com.example.modul5.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gunung")
data class GunungEntity(
    @PrimaryKey val name: String,
    val lokasi: String,
    val deskripsi: String,
    val link: String,
    val image: String,
    val isFavorite: Boolean
)