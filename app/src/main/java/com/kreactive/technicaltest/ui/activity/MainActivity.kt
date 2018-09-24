package com.kreactive.technicaltest.ui.activity

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.ui.fragment.ListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class MainActivity : AppCompatActivity() {

    lateinit var bottomSheetBehavior : BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setFragment() {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.activity_main_fragment, ListFragment())

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
