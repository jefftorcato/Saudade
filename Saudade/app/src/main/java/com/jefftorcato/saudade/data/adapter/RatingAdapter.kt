package com.jefftorcato.saudade.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.models.Rating
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import java.text.SimpleDateFormat


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
        var ratingDate: TextView = itemView.findViewById(R.id.rating_item_date)

        private val datePattern: String = "dd/MM/yyy"
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(datePattern)


        fun bind(rating: Rating) {
            nameView.text = rating.userName
            ratingBar.rating = rating.rating.toFloat()
            textView.text = rating.text
            ratingDate.text = simpleDateFormat.format(rating.timestamp!!)
        }
    }

}