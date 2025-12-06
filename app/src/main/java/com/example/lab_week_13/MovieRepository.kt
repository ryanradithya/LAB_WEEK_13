package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDao
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService, private val movieDatabase: MovieDatabase) {
    private val apiKey = "2e6f08f627aded75aeae4451f6f158fa"

    // LiveData that contains a list of movies
//    private val movieLiveData = MutableLiveData<List<Movie>>()
//    val movies: LiveData<List<Movie>>
//        get() = movieLiveData
//
//    // LiveData that contains an error message
//    private val errorLiveData = MutableLiveData<String>()
//    val error: LiveData<String>
//        get() = errorLiveData
    // fetch movies from the API
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Check if there are movies saved in the database
            val movieDao: MovieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()
            // If there are no movies saved in the database,
            // fetch the list of popular movies from the API
            if(savedMovies.isEmpty()) {
                val movies = movieService.getPopularMovies(apiKey).results
                // save the list of popular movies to the database
                movieDao.addMovies(movies)
                // emit the list of popular movies from the API
                emit(movies)
            } else {
                // If there are movies saved in the database,
                // emit the list of saved movies from the database
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }

}