package de.neugelb.presentation.ui.movies

import androidx.lifecycle.MutableLiveData
import de.neugelb.domain.entity.response.Result
import de.neugelb.domain.usecase.movies.GetMoviesUseCase
import de.neugelb.presentation.ui.base.BaseViewModel

class MovieViewModel(private val getMoviesUseCase: GetMoviesUseCase) : BaseViewModel() {

    val moviesList = MutableLiveData<List<Result>>()
    private var page = 1

    fun getMovies() = doInBackground {
        getMoviesUseCase.getMovies(page).doOnSuccess {
            page++
            moviesList.postValue(it)
        }
    }
}