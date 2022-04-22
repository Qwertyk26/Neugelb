package de.neugelb.presentation.ui.movies

import androidx.lifecycle.MutableLiveData
import de.neugelb.domain.entity.response.Result
import de.neugelb.domain.usecase.search.SearchMovieUseCase
import de.neugelb.presentation.ui.base.BaseViewModel

class SearchMovieViewModel(private val searchMovieUseCase: SearchMovieUseCase): BaseViewModel() {

    val searchList = MutableLiveData<List<Result>>()
    private var page = 1

    fun searchMovie(query: String, page: Int) = doInBackground {
        searchMovieUseCase.searchMovies(page, query).doOnSuccess {
            searchList.postValue(it)
        }
    }

    fun searchMoreMovies(query: String) = doInBackground {
        searchMovieUseCase.searchMovies(page, query).doOnSuccess {
            page++
            searchList.postValue(it)
        }
    }
}