package de.neugelb.presentation.ui.movies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.neugelb.R
import de.neugelb.databinding.FragmentDetailMovieBinding
import de.neugelb.domain.entity.response.Result
import kotlinx.android.synthetic.main.fragment_detail_movie.*

class MovieDetailFragment : BottomSheetDialogFragment() {

    private var movieItem: Result? = null
    private lateinit var binding: FragmentDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieItem = arguments?.getParcelable("movieItem")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load("${requireContext().getString(R.string.posters_base_url)}${movieItem?.posterPath}")
            .into(poster_image)
        movie_title.text = movieItem?.originalTitle
        movie_description.text = movieItem?.overview
        vote_text.text = "${movieItem?.voteAverage}"
    }
}