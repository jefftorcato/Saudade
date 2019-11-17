package com.jefftorcato.saudadeadmin.ui.dialog

import android.content.Context
import android.text.TextUtils
import com.google.firebase.firestore.Query
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.models.Event

class Filters {
    private var category: String? = ""
    private var city : String? = ""
    private var sortBy : String? = ""
    private var sortDirection : Query.Direction?

    constructor() {
        this.category = null
        this.city = null
        this.sortBy = null
        this.sortDirection = null
    }

    companion object {
        fun getDefault():Filters {
            val filters = Filters()
            filters.sortBy = Event.FIELD_AVG_RATING
            filters.sortDirection = Query.Direction.DESCENDING

            return filters
        }
    }

    fun hasCategory(): Boolean {
        return !TextUtils.isEmpty(category)
    }

    fun hasCity(): Boolean {
        return !TextUtils.isEmpty(city)
    }

    fun hasSortBy(): Boolean {
        return !TextUtils.isEmpty(sortBy)
    }

    fun getSearchDescription(context: Context): String {
        val desc = StringBuilder()

        if (category == null && city == null) {
            desc.append("<b>")
            desc.append("All events")
            desc.append("</b>")
        }

        if (category != null) {
            desc.append("<b>")
            desc.append(category)
            desc.append("</b>")
        }

        if (category != null && city != null) {
            desc.append(" in ")
        }

        if (city != null) {
            desc.append("<b>")
            desc.append(city)
            desc.append("</b>")
        }

        return desc.toString()
    }

    fun getOrderDescription(context: Context): String {
        return if (Event.FIELD_POPULARITY == sortBy) {
            context.getString(R.string.sorted_by_popularity)
        } else {
            context.getString(R.string.sorted_by_rating)
        }
    }
}