import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import api.GalleryItem // Correct import
import com.bignerdranch.android.cs4750_movieapp.R
import com.bumptech.glide.Glide

class RecommendedMoviesAdapter(private val movies: List<GalleryItem>, private val onMovieClick: (GalleryItem) -> Unit) :
    RecyclerView.Adapter<RecommendedMoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: GalleryItem) {
            // Load the movie poster using the correct property name
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(moviePoster)
            movieTitle.text = movie.title

            // Set a click listener for the item
            itemView.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_recommended, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}
