package com.bignerdranch.android.cs4750_movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import api.MovieDetail
import com.bumptech.glide.Glide
import com.bignerdranch.android.cs4750_movieapp.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val movieRepository = MovieRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId") ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            val movieDetail = movieRepository.fetchMovieDetails(movieId)
            movieDetail?.let { populateUI(it) }
        }
    }

    private fun populateUI(movieDetail: MovieDetail) {
        binding.movieTitle.text = movieDetail.title
        binding.movieRating.text = "Rating: ${movieDetail.vote_average}"
        binding.movieReleaseDate.text = "Release Date: ${movieDetail.release_date}"
        binding.movieOverview.text = movieDetail.overview
        val imageUrl = "https://image.tmdb.org/t/p/w500${movieDetail.poster_path}"
        Glide.with(binding.root.context).load(imageUrl).into(binding.moviePoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
