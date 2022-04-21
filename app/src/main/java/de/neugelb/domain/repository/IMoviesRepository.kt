package de.neugelb.domain.repository

import de.neugelb.domain.entity.response.Result

interface IMoviesRepository {

    suspend fun getMovies(page: Int): List<Result>?
}