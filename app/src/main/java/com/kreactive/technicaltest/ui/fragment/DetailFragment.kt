package com.kreactive.technicaltest.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.manager.ViewBinderManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.ui.fragment.base.BaseFragment
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import com.kreactive.technicaltest.viewmodel.DetailFragmentViewModel
import org.kodein.di.generic.instance
import timber.log.Timber

class DetailFragment : BaseFragment() {

    private val viewModel: DetailFragmentViewModel by instance(arg = this)

    companion object {

        val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: String) : Fragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString(MOVIE_ID,movieId)
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

    private fun loadDatas(){
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

    private fun onMovieChanged(movie : Movie){
        Timber.i(movie.toString())
    }
}