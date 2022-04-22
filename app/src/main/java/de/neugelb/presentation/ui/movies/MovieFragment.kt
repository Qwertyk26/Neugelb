package de.neugelb.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.neugelb.R
import de.neugelb.databinding.FragmentMoviesBinding
import de.neugelb.presentation.ui.base.BaseFragment
import de.neugelb.presentation.ui.base.listener.RecyclerScrollListener
import de.neugelb.presentation.ui.extensions.hideKeyboard
import de.neugelb.presentation.ui.movies.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment() {

    override val viewModel by viewModel<MovieViewModel>()
    private val searchMovieView by viewModel<SearchMovieViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter
    private var onScrollListener: RecyclerScrollListener? = null
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies()
        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        val layoutManager = GridLayoutManager(context, 3,
            GridLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(arrayListOf()).apply {
            onItemClick = { result ->
                navController.navigate(R.id.movie_detail_fragment, bundleOf("movieItem" to result))
            }
        }
        recycler_view.adapter = moviesAdapter
        onScrollListener = RecyclerScrollListener(layoutManager) {
            if (search_et.text.toString().isEmpty()) {
                viewModel.getMovies()
            } else {
                searchMovieView.searchMoreMovies("${search_et.text}")
            }
        }.also {
            recycler_view.addOnScrollListener(it)
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    if (search_et.hasFocus()) {
                        search_et.clearFocus()
                        hideKeyboard()
                    }
                }
            }
        })
        search_et.doAfterTextChanged {
            if (it.toString().isEmpty()) {
                moviesAdapter.clear()
                progress_bar.isVisible = false
                moviesAdapter.addList(viewModel.moviesList.value ?: arrayListOf())
            } else {
                progress_bar.isVisible = true
                moviesAdapter.clear()
                searchMovieView.searchMovie("$it", 1)
            }
        }
    }

    private fun setupObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty().not()) {
                progress_bar.isGone = true
                moviesAdapter.addList(movies)
            }
        }
        searchMovieView.searchList.observe(viewLifecycleOwner) { searchList ->
            if (searchList.isNullOrEmpty().not()) {
                progress_bar.isGone = true
                moviesAdapter.addList(searchList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.moviesList.removeObservers(this)
        searchMovieView.searchList.removeObservers(this)
    }
}