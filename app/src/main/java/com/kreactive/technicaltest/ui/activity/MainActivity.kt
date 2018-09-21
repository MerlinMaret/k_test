package com.kreactive.technicaltest.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.ui.fragment.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
    }

    private fun setFragment(){
        val fragmentTransition = supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.activity_main_fragment, ListFragment())

        fragmentTransition.commit()
    }
}
