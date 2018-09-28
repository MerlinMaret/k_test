package com.kreactive.technicaltest.ui.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.model.Movie
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import timber.log.Timber

class MovieAdapter (val callback : MovieAdapter.Callback) : PagedListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(v, callback)
    }

    override fun onBindViewHolder(viewHodler: MovieViewHolder, position: Int) {
        viewHodler.bind(getItem(position))
    }

    interface Callback{
        fun onItemClickListener(movie : Movie, sharedElements : List<Pair<View,String>>)
    }
}

class MovieViewHolder(view: View, val callback : MovieAdapter.Callback) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie?) {
        itemView.item_movie_title.text = movie?.title
        itemView.item_movie_year.text = movie?.year

        Picasso.get()
                .load(movie?.posterUrl)
                .error(R.drawable.ic_noposter)
                .into(itemView.item_movie_iv_poster, object : Callback {
                        override fun onSuccess() {
                            Timber.d("Success")
                        }

                        override fun onError(e: Exception) {
                            Timber.e(e)
                        }
                    }
                )
        itemView.setOnClickListener { view ->
            movie?.let { callback.onItemClickListener(it, getSharedElements())}
        }
    }

    private fun getSharedElements() : List<Pair<View,String>> {
        return emptyList()
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        val isSameState = oldItem.equals(newItem)
        return isSameState
    }
}