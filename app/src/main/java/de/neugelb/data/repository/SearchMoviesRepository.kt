package de.neugelb.data.repository

import de.neugelb.domain.entity.response.Result
import de.neugelb.domain.repository.ISearchMoviesRepository

class SearchMoviesRepository : AbstractRepository(), ISearchMoviesRepository {
    override suspend fun searchMovies(page: Int, query: String): List<Result>? {
        return server.searchMovie(page, query).results
    }
}