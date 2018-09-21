package com.kreactive.technicaltest.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.manager.ViewBinderManager
import com.kreactive.technicaltest.model.Movie
import com.kreactive.technicaltest.ui.adapter.MovieAdapter
import com.kreactive.technicaltest.ui.fragment.base.BaseFragment
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import com.kreactive.technicaltest.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import org.kodein.di.generic.instance
import android.view.MenuInflater
import timber.log.Timber


class ListFragment : BaseFragment(){

    private val viewModel: ListFragmentViewModel by instance(arg = this)

    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeViewModel()
    }

    private fun initRecyclerView(){
        fragment_list_recyclerview.adapter = movieAdapter
        fragment_list_swiperefresh.setOnRefreshListener {
            //TODO Reload datas
        }
    }

    private fun subscribeViewModel(){
        ViewBinderManager.subscribeValue(
                lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY),
                viewModel.moviesObservable,
                {
                    onMoviesChanged(it)
                }
        )
    }

    private fun onMoviesChanged(list : List<Movie>){
        movieAdapter.submitList(list)
        fragment_list_swiperefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}