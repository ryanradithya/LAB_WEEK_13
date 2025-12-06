package com.example.lab_week_13

import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(private val movieService: MovieService) {
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
            // emit the list of popular movies from the API
            emit(movieService.getPopularMovies(apiKey).results)
            // use Dispatchers.IO to run this coroutine on a shared pool of
            //threads
        }.flowOn(Dispatchers.IO)
    }

}