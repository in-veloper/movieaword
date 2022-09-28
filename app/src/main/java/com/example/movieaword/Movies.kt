package com.example.movieaword

import androidx.room.*

@Entity(tableName = "tb_movies")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var movieName: String,
    var posterPath: String
)
