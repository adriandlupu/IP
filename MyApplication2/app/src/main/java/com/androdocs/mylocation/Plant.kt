package com.androdocs.mylocation

data class Plant(var name: String, var range: String, val locatii: MutableList<Locatie>)

data class Locatie(var lat: String, var long: String)