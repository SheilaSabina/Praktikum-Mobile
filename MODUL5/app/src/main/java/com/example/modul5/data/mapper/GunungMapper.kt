package com.example.modul5.data.mapper

import com.example.modul5.data.model.Gunung
import com.example.modul5.data.local.entity.GunungEntity

fun Gunung.toEntity(): GunungEntity {
    return GunungEntity(
        name = name,
        lokasi = lokasi,
        deskripsi = deskripsi,
        link = link,
        image = image,
        isFavorite = isFavorite
    )
}

fun GunungEntity.toModel(): Gunung {
    return Gunung(
        name = name,
        lokasi = lokasi,
        deskripsi = deskripsi,
        link = link,
        image = image,
        isFavorite = isFavorite
    )
}
