package tn.yassin.oneblood.data

import androidx.annotation.DrawableRes

const val Nom = "NAME"
const val Location = "Location"
const val Numero = "Numero"
const val Longitude = "Longitude"
const val Latitude = "Latitude"

data class Inofrmations(
    val Nom: String,
    val Location: String,
    val Numero: String,
    val Longitude: String,
    val Latitude: String
)