package de.neugelb.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import de.neugelb.R
import de.neugelb.presentation.ui.base.BaseFragment
import de.neugelb.presentation.ui.base.listener.RecyclerScrollListener
import de.neugelb.presentation.ui.movies.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment() {

    override val viewModel by viewModel<MovieViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter
    private var onScrollListener: RecyclerScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
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
            viewModel.getMovies()
        }.also {
            recycler_view.addOnScrollListener(it)
        }
    }

    private fun setupObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty().not()) {
                progress_bar.isGone = true
                moviesAdapter.addList(movies)
            }
        }
    }
}