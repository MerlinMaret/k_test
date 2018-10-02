package com.kreactive.technicaltest.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.transition.TransitionSet
import android.view.Gravity
import com.kreactive.technicaltest.R
import android.view.View
import com.kreactive.technicaltest.manager.TransitionManager
import com.kreactive.technicaltest.ui.activity.base.BaseActivity
import com.kreactive.technicaltest.ui.fragment.DetailFragment
import com.kreactive.technicaltest.ui.fragment.ListFragment
import com.kreactive.technicaltest.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    val MOVE_DEFAULT_TIME: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            setFragment(SearchFragment())
        }
    }

    fun setFragment(
            nextFragment: Fragment,
            backStackName: String? = null,
            sharedElements: List<Pair<View, String>> = emptyList(),
            exitTransition: Any = TransitionManager.getSlideTransition(0, MOVE_DEFAULT_TIME),
            reenterTransition: Any = TransitionManager.getSlideTransition(Math.max(MOVE_DEFAULT_TIME, MOVE_DEFAULT_TIME), MOVE_DEFAULT_TIME),
            enterTransition: Any = TransitionManager.getSlideTransition(Math.max(MOVE_DEFAULT_TIME, MOVE_DEFAULT_TIME), MOVE_DEFAULT_TIME, Gravity.RIGHT),
            returnTransition: Any = TransitionManager.getSlideTransition(0, MOVE_DEFAULT_TIME, Gravity.RIGHT),
            sharedElementsTransition: TransitionSet = TransitionManager.getSharedElementsTransition(0, MOVE_DEFAULT_TIME,this)
    ) {
        val previousFragment = supportFragmentManager.findFragmentById(R.id.activity_main_fragment)

        val fragmentTransition = supportFragmentManager
                .beginTransaction()

        // region Transitions

        previousFragment?.let {
            previousFragment.exitTransition = exitTransition
            previousFragment.reenterTransition = reenterTransition
        }

        nextFragment.enterTransition = enterTransition
        nextFragment.returnTransition = returnTransition

        nextFragment.sharedElementEnterTransition = sharedElementsTransition

        for (sharedElement in sharedElements) {
            fragmentTransition.addSharedElement(sharedElement.first, sharedElement.second)
        }

        //endregion

        backStackName?.let {
            fragmentTransition.addToBackStack(backStackName)
        }

        fragmentTransition.replace(R.id.activity_main_fragment, nextFragment)


        fragmentTransition.commit()
    }

    fun showFab(icon : Drawable?, onFabClick : (() -> Unit)?){
        activity_main_fab.setImageDrawable(icon)
        activity_main_fab.show()
        activity_main_fab.setOnClickListener{
            onFabClick?.invoke()
        }
    }

    fun hideFab(){
        activity_main_fab.hide()
    }
}
