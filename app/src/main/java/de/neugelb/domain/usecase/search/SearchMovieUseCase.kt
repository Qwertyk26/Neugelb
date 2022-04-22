package de.neugelb.domain.usecase.search

import de.neugelb.domain.repository.ISearchMoviesRepository
import de.neugelb.domain.usecase.AbstractUseCase

class SearchMovieUseCase(
    private val searchMovieRepository: ISearchMoviesRepository
) : AbstractUseCase() {

    suspend fun searchMovies(page: Int, query: String) = execute {
        return@execute searchMovieRepository.searchMovies(page, query)
    }
}