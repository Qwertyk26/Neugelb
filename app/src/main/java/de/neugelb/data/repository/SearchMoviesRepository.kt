package de.neugelb.data.repository

import de.neugelb.domain.entity.response.Result
import de.neugelb.domain.repository.IMoviesRepository

class SearchMoviesRepository(): IMoviesRepository {
    override suspend fun getMovies(page: Int): List<Result>? {
        TODO("Not yet implemented")
    }
}