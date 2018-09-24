package com.kreactive.technicaltest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kreactive.technicaltest.R
import kotlinx.android.synthetic.main.item_year.view.*
import java.util.*
import kotlin.collections.ArrayList

class YearAdapter (val context : Context) : BaseAdapter() {

    var years: List<String>

    init {
        val minYear = 1900
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val list = ArrayList<String>()
        list.add(context.getString(R.string.year))
        for(i in (minYear..thisYear).reversed()){
            list.add(i.toString())
        }
        years = list
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_year, null)

        view.item_year_text.text = getItem(position).toString()
        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getItem(position: Int): Any {
        return years[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return years.size
    }

}