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

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getCity(): String {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getCategory(): String {
        return category
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun getPhoto(): String {
        return photo
    }

    fun setPhoto(photo: String) {
        this.photo = photo
    }

    fun getNumRatings(): Int {
        return numRatings
    }

    fun setNumRatings(numRatings: Int) {
        this.numRatings = numRatings
    }

    fun getAvgRating(): Double {
        return avgRating
    }

    fun setAvgRating(avgRating: Double) {
        this.avgRating = avgRating
    }

}