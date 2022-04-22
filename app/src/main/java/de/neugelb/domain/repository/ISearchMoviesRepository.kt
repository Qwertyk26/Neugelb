package de.neugelb.domain.repository

import de.neugelb.domain.entity.response.Result

interface ISearchMoviesRepository {
    suspend fun searchMovies(page: Int, query: String) : List<Result>?
}