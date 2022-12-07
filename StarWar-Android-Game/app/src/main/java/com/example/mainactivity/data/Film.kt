package com.example.mainactivity.data

data class Film(
    val title: String,
    val episode_id: Int,
    val director: String,
    val producer: String,
    val release_date: String

)

data class SevenFilms(
    val results: List<Film>
)