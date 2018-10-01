package com.kreactive.technicaltest.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
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
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.paging.PagedList
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.ui.dialog.BottomSheetFilterFragment
import timber.log.Timber

class ListFragment : BaseFragment(), BottomSheetFilterFragment.Callback, MovieAdapter.Callback {

    private val viewModel: ListFragmentViewModel by instance(arg = this)

    private val movieAdapter = MovieAdapter(this)

    //region init

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

        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.app_name)
        initRecyclerView()
        subscribeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_menu, menu)

        initSearchView(menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initRecyclerView() {
        fragment_list_recyclerview.adapter = movieAdapter
        fragment_list_swiperefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    private fun initSearchView(menu: Menu?) {

        val searchView = (menu?.findItem(R.id.menu_search)?.actionView as SearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                viewModel.setSearchText(text ?: "")
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }
        }
        )
    }

    //endregion

    //region subscriptions

    private fun subscribeViewModel() {
        ViewBinderManager.subscribeValue(
                lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY),
                viewModel.movies,
                {
                    onMoviesChanged(it)
                }
        )


        ViewBinderManager.subscribeValue(
                lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY),
                viewModel.searchingStatus,
                {
                    onSearchStatusChanged(it)
                }
        )
    }

    private fun onMoviesChanged(list: PagedList<Movie>) {
        movieAdapter.submitList(list)
    }

    private fun onSearchStatusChanged(networkStatus: NetworkStatus) {
        when (networkStatus) {
            is NetworkStatus.InProgress -> fragment_list_swiperefresh?.isRefreshing = true
            is NetworkStatus.Success -> {
                fragment_list_swiperefresh?.isRefreshing = false
                fragment_list_recyclerview?.visibility = VISIBLE
                fragment_list_tv_error?.visibility = GONE
            }
            is NetworkStatus.Error<*> -> {
                fragment_list_swiperefresh?.isRefreshing = false
                fragment_list_tv_error?.visibility = VISIBLE
                fragment_list_recyclerview?.visibility = GONE
                fragment_list_tv_error?.text = viewModel.getTextError(networkStatus.error)
            }
        }
    }

    //endregion

    //region Filter

    override fun setFab() {
        setFab(
                R.drawable.ic_sort,
                {
                    showBottomSheet()
                }
        )
    }

    private fun showBottomSheet() {
        BottomSheetFilterFragment().show(fragmentManager,"BottomSheetFilterFragment", viewModel.getType(), viewModel.getYear(), this)
    }

    //endregion


    //region Button Sheet Callback

    override fun onTypeCheckChanged(type: Type?) {
        type?.let { viewModel.setSearchType(it) }
    }

    override fun onYearChanged(year: String?) {
        year?.let { viewModel.setSearchYear(it) }
    }

    //endregion

    //region List Callback

    override fun onItemClickListener(movie: Movie, sharedElements :  List<Pair<View,String>>) {
        (activity as? MainActivity)?.setFragment(DetailFragment.newInstance(movie.imdbID), DetailFragment::class.java.name, sharedElements = sharedElements)
    }

    //endregion

}