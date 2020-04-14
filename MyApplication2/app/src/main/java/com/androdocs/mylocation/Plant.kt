package com.androdocs.mylocation

data class Plant(var name: String, val locatii: MutableList<Locatie>)

data class Locatie(var lat: String, var long: String)