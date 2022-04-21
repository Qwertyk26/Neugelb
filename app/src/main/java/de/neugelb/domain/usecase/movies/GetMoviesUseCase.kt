package de.neugelb.domain.usecase.movies

import de.neugelb.domain.repository.IMoviesRepository
import de.neugelb.domain.usecase.AbstractUseCase

class GetMoviesUseCase(private val movieRepository: IMoviesRepository) : AbstractUseCase() {

    suspend fun getMovies(page: Int) = execute {
        return@execute movieRepository.getMovies(page)
    }
}