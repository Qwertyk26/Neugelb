package de.neugelb.domain.usecase.search

import de.neugelb.domain.repository.IMoviesRepository
import de.neugelb.domain.usecase.AbstractUseCase

class SearchMovieUseCase(
    private val movieRepository: IMoviesRepository
) : AbstractUseCase() {
}