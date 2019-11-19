package com.jefftorcato.saudade.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.models.Event
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class EventAdapter(query: Query, private val mListener: OnEventSelectedListener) :
    FirestoreAdapter<EventAdapter.ViewHolder>(query) {

    override fun onError(e: FirebaseFirestoreException) {

    }

    override fun onDatachanged() {
        this.mListener.onDataChange()
    }

    interface OnEventSelectedListener {

        fun onEventSelected(event: DocumentSnapshot)
        fun onDataChange()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), mListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.event_item_image)
        var nameView: TextView = itemView.findViewById(R.id.event_item_name)
        var ratingBar: MaterialRatingBar = itemView.findViewById(R.id.event_item_rating)
        var numRatingsView: TextView = itemView.findViewById(R.id.event_item_num_ratings)
        var categoryView: TextView = itemView.findViewById(R.id.event_item_category)
        var cityView: TextView = itemView.findViewById(R.id.event_item_city)

        fun bind(snapshot: DocumentSnapshot, listener: OnEventSelectedListener) {
            val event: Event? = snapshot.toObject(Event::class.java)
            val resources = itemView.resources

            if (event != null) {
                Glide.with(imageView.context)
                    .load(event.getPhoto())
                    .into(imageView)

                nameView.text = event.getName()
                ratingBar.rating = event.getAvgRating().toFloat()
                cityView.text = event.getCity()
                categoryView.text = event.getCategory()
                numRatingsView.text =
                    resources.getString(R.string.fmt_num_ratings, event.getNumRatings())
            }
            itemView.setOnClickListener {
                listener.onEventSelected(snapshot)
            }


        }

    }
}