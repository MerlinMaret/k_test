package com.kreactive.technicaltest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.api.NetworkStatus
import com.kreactive.technicaltest.manager.ViewBinderManager
import com.kreactive.technicaltest.ui.activity.MainActivity
import com.kreactive.technicaltest.ui.fragment.base.BaseFragment
import com.kreactive.technicaltest.utils.RxLifecycleDelegate
import com.kreactive.technicaltest.viewmodel.SearchFragmentViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.generic.instance

class SearchFragment : BaseFragment(){

    private val viewModel: SearchFragmentViewModel by instance(arg = this)

    //region init


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_search, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAction()
        subscribeViewModel()
    }

    private fun setAction(){
        fragment_search_button.setOnClickListener {
            viewModel.searchTextRelay.accept(fragment_search_et.text.toString())
        }
    }

    private fun subscribeViewModel() {
        ViewBinderManager.subscribeValue(
                lifecycle(RxLifecycleDelegate.FragmentEvent.DESTROY),
                viewModel.searchingStatus,
                {
                    onSearchStatusChanged(it)
                }
        )
    }

    private fun onSearchStatusChanged(networkStatus: NetworkStatus) {
        when(networkStatus){
            is NetworkStatus.Success -> {
                fragment_search_progress.visibility = GONE
                fragment_search_errors.visibility = GONE
                (activity as? MainActivity)?.setFragment(ListFragment())
            }
            is NetworkStatus.Error<*> -> {
                fragment_search_progress.visibility = GONE
                fragment_search_errors.visibility = VISIBLE
                fragment_search_errors.text = viewModel.getTextError(networkStatus.error)
            }
            is NetworkStatus.InProgress -> {
                fragment_search_progress.visibility = VISIBLE
                fragment_search_errors.visibility = GONE
            }
        }
    }
}