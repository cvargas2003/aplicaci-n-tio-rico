package com.cesar.tiorico.model

data class Player(
    val nombre: String,
    val dinero: Int = 1000,
    val activo: Boolean = true
)