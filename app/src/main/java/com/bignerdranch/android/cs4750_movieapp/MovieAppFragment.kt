package com.bignerdranch.android.cs4750_movieapp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import api.Genre
import api.MovieGenre
import com.bignerdranch.android.cs4750_movieapp.databinding.FragmentMovieAppBinding
import kotlinx.coroutines.launch


private const val TAG = "MovieAppFragment"

class MovieAppFragment : Fragment() {
    private var _binding: FragmentMovieAppBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val movieAppViewModel: MovieAppViewModel by viewModels()
    private var genreList: List<MovieGenre> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMovieAppBinding.inflate(inflater, container, false)
        binding.movieList.layoutManager = GridLayoutManager(context, 3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieAppViewModel.genres.collect { genres ->
                    if (genres.isNotEmpty()) {
                        genreList = listOf(MovieGenre(id = -1, name = "All Movies")) + genres
                        val genreNames = genreList.map { it.name }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            genreNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.genreSpinner.adapter = adapter
                    } else {
                        Log.e(TAG, "Failed to load genres or genres are empty.")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieAppViewModel.galleryItems.collect { items ->
                    binding.movieList.adapter = MovieListAdapter(items) {movieId ->
                        val action = MovieAppFragmentDirections.showMovieDetail(movieId)
                        findNavController().navigate(action)
                    }
                }
            }
        }

        // SearchView listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    Log.d("SearchView", "Query submitted: $query")
                    movieAppViewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally update suggestions dynamically
                return false
            }
        })

            binding.genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedGenreId = genreList[position].id
                    if (selectedGenreId == -1) {
                        // Fetch popular or all movies when "All Movies" is selected
                        movieAppViewModel.fetchPopularMovies()
                    } else {
                        // Fetch movies by the selected genre
                        movieAppViewModel.getMoviesByGenre(selectedGenreId)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Optionally handle the case when no genre is selected
                }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

