package de.neugelb.data.repository

import de.neugelb.domain.entity.response.Result
import de.neugelb.domain.repository.IMoviesRepository

class MoviesRepository: AbstractRepository(), IMoviesRepository {
    override suspend fun getMovies(page: Int) : List<Result>? {
        return server.getPopularMovies(page).results
    }
}