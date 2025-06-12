package com.example.modul5.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serializable as JavaSerializable

@Serializable
data class Gunung(
    @SerialName("name")
    val name: String,

    @SerialName("lokasi")
    val lokasi: String,

    @SerialName("deskripsi")
    val deskripsi: String,

    @SerialName("link")
    val link: String,

    @SerialName("image_url")
    val image: String,

    val isFavorite: Boolean = false
)  : JavaSerializable
