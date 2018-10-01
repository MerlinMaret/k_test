package com.kreactive.technicaltest.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.manager.ViewBinderManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.ui.fragment.base.BaseFragment
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import com.kreactive.technicaltest.viewmodel.DetailFragmentViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import org.kodein.di.generic.instance
import timber.log.Timber



class DetailFragment : BaseFragment() {

    private val viewModel: DetailFragmentViewModel by instance(arg = this)

    companion object {

        val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: String): Fragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString(MOVIE_ID, movieId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_detail, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDatas()
        subscribeViewModel()
    }


    private fun loadDatas() {
        viewModel.loadDatas(arguments?.getString(MOVIE_ID))
    }

    private fun subscribeViewModel() {
        ViewBinderManager.subscribeValue(
                lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY),
                viewModel.movie,
                {
                    onMovieChanged(it)
                }
        )
    }

    private fun onMovieChanged(movie: Movie?) {
        Timber.i(movie.toString())
        (activity as? MainActivity)?.supportActionBar?.title = movie?.title
        movie?.let {
            showDatas(movie)
        }
    }

    private fun showDatas(movie: Movie) {
        Picasso.get()
                .load(movie.posterUrl)
                .error(R.drawable.ic_noposter)
                .into(fragment_detail_iv_poster, object : Callback {
                    override fun onSuccess() {
                        Timber.d("Success")
                    }

                    override fun onError(e: Exception) {
                        Timber.e(e)
                    }
                }
                )
        setTextNullable(fragment_detail_year, movie.year)
        setTextNullable(fragment_detail_release, movie.released)
        setTextNullable(fragment_detail_runtime, movie.runtime)
        setTextNullable(fragment_detail_genre, movie.genre)
        setTextNullable(fragment_detail_tv_rate, movie.rated)
        setTextNullable(fragment_detail_tv_director, movie.director)
        setTextNullable(fragment_detail_tv_writer, movie.writer)
        setTextNullable(fragment_detail_tv_actors, movie.actors)
        setTextNullable(fragment_detail_tv_plot, movie.plot)
        setTextNullable(fragment_detail_tv_language, movie.language)
        setTextNullable(fragment_detail_tv_country, movie.country)
        setTextNullable(fragment_detail_tv_awards, movie.awards)
        setTextNullable(fragment_detail_tv_metascore, movie.metascore)
        setTextNullable(fragment_detail_tv_imdbRating, movie.imdbRating)
        setTextNullable(fragment_detail_tv_imdbVotes, movie.imdbVotes)
        setTextNullable(fragment_detail_tv_dvd, movie.DVD)
        setTextNullable(fragment_detail_tv_boxOffice, movie.boxOffice)
        setTextNullable(fragment_detail_tv_production, movie.production)
        setTextNullable(fragment_detail_tv_website, movie.website)

        //TODO show details rate
    }

    private fun setTextNullable(textView: TextView, text: String?) {

        val isEmpty = TextUtils.isEmpty(text)
        val isNA = text?.equals("N/A") ?: true
        if (isEmpty || isNA) {
            textView.visibility = GONE
        } else {
            textView.visibility = VISIBLE
            textView.text = text
        }

    }
}