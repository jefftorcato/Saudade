package com.jefftorcato.saudadeadmin.data.models

class Event constructor(
    private var name: String = "",
    private var city: String = "",
    private var category: String = "",
    private var photo: String = "",
    private var numRatings: Int = 0,
    private var avgRating: Double = 0.0
) {

    companion object {
        val FIELD_CITY: String = "city"
        val FIELD_CATEGORY = "category"
        val FIELD_POPULARITY = "numRatings"
        val FIELD_AVG_RATING = "avgRatings"
    }

}