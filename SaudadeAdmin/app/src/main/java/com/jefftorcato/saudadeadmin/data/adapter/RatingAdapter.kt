package com.jefftorcato.saudadeadmin.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.models.Rating
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class RatingAdapter(query: Query) : FirestoreAdapter<RatingAdapter.ViewHolder>(query) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rating, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position).toObject(Rating::class.java)!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameView: TextView = itemView.findViewById(R.id.rating_item_name)
        var ratingBar: MaterialRatingBar = itemView.findViewById(R.id.rating_item_rating)
        var textView: TextView = itemView.findViewById(R.id.rating_item_text)

        fun bind(rating: Rating) {
            nameView.text = rating.userName
            ratingBar.rating = rating.rating.toFloat()
            textView.text = rating.text
        }
    }

}