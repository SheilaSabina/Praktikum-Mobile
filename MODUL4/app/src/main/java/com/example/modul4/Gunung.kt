package com.example.modul4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gunung(
    val image: Int,
    val name: String,
    val lokasi: String,
    val deskripsi: String,
    val link: String


): Parcelable