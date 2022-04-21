package de.neugelb.di

import de.neugelb.data.repository.MoviesRepository
import de.neugelb.data.server.ServerApiConfigurator
import de.neugelb.domain.repository.IMoviesRepository
import de.neugelb.domain.usecase.movies.GetMoviesUseCase
import de.neugelb.presentation.ui.base.EmptyViewModel
import de.neugelb.presentation.ui.movies.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoviesUseCase(get()) }
}
val repositoryModule = module {
    single { ServerApiConfigurator().configureServerApi(get()) }
    single<IMoviesRepository> { MoviesRepository() }
}

val viewModelModule = module {
    viewModel { EmptyViewModel() }
    viewModel { MovieViewModel(get()) }
}

val koinModules = useCaseModule + repositoryModule + viewModelModule