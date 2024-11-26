package com.bignerdranch.android.cs4750_movieapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
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
                movieAppViewModel.galleryItems.collect { items ->
                    binding.movieList.adapter = MovieListAdapter(items)
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieAppViewModel.galleryItems.collect { items ->
                    // Update the RecyclerView with the new list of movies
                    binding.movieList.adapter = MovieListAdapter(items)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

