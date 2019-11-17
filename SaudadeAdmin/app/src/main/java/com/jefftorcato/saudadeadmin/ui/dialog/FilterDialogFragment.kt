package com.jefftorcato.saudadeadmin.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

import com.google.firebase.firestore.Query
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.models.Event

class FilterDialogFragment: DialogFragment(), View.OnClickListener {


    companion object{
        val TAG: String = "FilterDialog"
    }

    interface FilterListener {
        fun onFilter(filters: Filters)
    }

    private lateinit var mRootView: View
    private lateinit var mCategorySpinner: Spinner
    private lateinit var mCitySpinner: Spinner
    private lateinit var mSortSpinner: Spinner

    private var mFilterListener: FilterListener? = null

    @Nullable override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.dialog_filters, container,false)
        mCategorySpinner = mRootView.findViewById(R.id.spinner_category)
        mCitySpinner = mRootView.findViewById(R.id.spinner_city)
        mSortSpinner = mRootView.findViewById(R.id.spinner_sort)

        mRootView.findViewById<Button>(R.id.button_search).setOnClickListener(this)
        mRootView.findViewById<Button>(R.id.button_cancel).setOnClickListener(this)

        return mRootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FilterListener) {
            mFilterListener = context
        }
    }

    override fun onResume() {
        super.onResume()

        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_search -> onSearchClicked()
            R.id.button_cancel -> onCancelClicked()
        }
    }

    fun onSearchClicked(){
        mFilterListener?.onFilter(getFilters())

        dismiss()
    }

    fun onCancelClicked() {
        dismiss()
    }

    private fun getSelectedCategory(): String? {
        val selected = mCategorySpinner.selectedItem as String
        return if (getString(R.string.value_any_category) == selected) {
            null
        } else {
            selected
        }
    }

    private fun getSelectedCity(): String? {
        val selected = mCitySpinner.selectedItem as String
        return if (getString(R.string.value_any_city) == selected) {
            null
        } else {
            selected
        }
    }

    private fun getSelectedSortBy(): String? {
        val selected = mSortSpinner.selectedItem as String
        if (getString(R.string.sort_by_rating) == selected) {
            return Event.FIELD_AVG_RATING
        }
        if (getString(R.string.sort_by_popularity) == selected) {
            Event.FIELD_POPULARITY
        }

        return null
    }

    private fun getSortDirection(): Query.Direction? {
        val selected = mSortSpinner.selectedItem as String
        if (getString(R.string.sort_by_rating) == selected) {
            return Query.Direction.DESCENDING
        }
        if (getString(R.string.sort_by_popularity) == selected) {
            return Query.Direction.DESCENDING
        }

        return null
    }

    fun resetFilters() {
        if (mRootView != null) {
            mCategorySpinner.setSelection(0)
            mCitySpinner.setSelection(0)
            mSortSpinner.setSelection(0)
        }
    }

    fun getFilters(): Filters {
        val filters = Filters()

        if(mRootView != null) {
            filters.setCategory(getSelectedCategory())
            filters.setCity(getSelectedCity())
            filters.setSortBy(getSelectedSortBy())
            filters.setSortDirection(getSortDirection())
        }

        return filters
    }

}