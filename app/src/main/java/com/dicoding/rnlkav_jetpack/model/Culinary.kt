package com.dicoding.rnlkav_jetpack.model

data class Culinary(
    val id: Long,
    val name: String,
    val description: String,
    val photoUrl: String,
    val origin: String,
    val price: String,
    val isFavorite: Boolean = false
)
