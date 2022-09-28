package com.example.movieaword

import androidx.room.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM tb_movies")
    fun getAll(): List<Movies>

    @Insert
    fun insertAll(entity: Movies)

    @Delete
    fun delete(movies: Movies)
}