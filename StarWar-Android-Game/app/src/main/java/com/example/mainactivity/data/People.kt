package com.example.mainactivity.data

data class People(
    val birth_year: String,
    val eye_color: String,
    val gender: String,
    val hair_color: String,
    val height: String,
    val mass: String,
    val name: String,
    val skin_color: String
)

data class AllPeople(
    val results: List<People>
)