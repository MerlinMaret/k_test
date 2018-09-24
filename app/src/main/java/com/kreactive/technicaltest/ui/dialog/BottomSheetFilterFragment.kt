package com.kreactive.technicaltest.ui.dialog

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.kreactive.technicaltest.R
import com.kreactive.technicaltest.model.Type
import com.kreactive.technicaltest.ui.adapter.YearAdapter
import kotlinx.android.synthetic.main.dialog_bottom_sheet_filter.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein

class BottomSheetFilterFragment : BottomSheetDialogFragment(), KodeinAware {
    override val kodein by closestKodein()
    private var callback: Callback? = null
    private var selectedType: Type? = null
    private var selectedYear: String? = null
    private var yearAdapter: YearAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_bottom_sheet_filter, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preset()
        setActions()
    }

    fun show(manager: FragmentManager?, tag: String?, selectedType: Type?, selectedYear: String?, callback: Callback) {
        this.callback = callback
        this.selectedType = selectedType
        this.selectedYear = selectedYear
        super.show(manager, tag)
    }

    private fun preset() {
        when (selectedType) {
            Type.movie -> dialog_bottom_filter_radiobutton_movie.isChecked = true
            Type.series -> dialog_bottom_filter_radiobutton_series.isChecked = true
            Type.episode -> dialog_bottom_filter_radiobutton_episode.isChecked = true
        }

        context?.let { yearAdapter = YearAdapter(it) }
        dialog_bottom_filter_s_year.adapter = yearAdapter
    }

    private fun setActions() {

        dialog_bottom_filter_radiogroup.setOnCheckedChangeListener { radioGroup, i ->
            val checkedId = radioGroup.checkedRadioButtonId
            when (checkedId) {
                R.id.dialog_bottom_filter_radiobutton_movie -> callback?.onTypeCheckChanged(Type.movie)
                R.id.dialog_bottom_filter_radiobutton_series -> callback?.onTypeCheckChanged(Type.series)
                R.id.dialog_bottom_filter_radiobutton_episode -> callback?.onTypeCheckChanged(Type.episode)
            }
        }

        dialog_bottom_filter_s_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //Nothing
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val year = yearAdapter?.getItem(position) as? String
                callback?.onYearChanged(year)
            }

        }
    }

    interface Callback {
        fun onTypeCheckChanged(type: Type?)
        fun onYearChanged(year: String?)
    }

}
