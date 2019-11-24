package com.jefftorcato.saudade.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.models.Booking
import java.text.SimpleDateFormat

class BookingAdapter(query: Query, private val mListener: OnBookingSelectedListener) :
    FirestoreAdapter<BookingAdapter.ViewHolder>(query) {

    interface OnBookingSelectedListener {

        fun onBookingSelected(booking: DocumentSnapshot)
        fun onDataChange()
    }

    override fun onDatachanged() {
        this.mListener.onDataChange()
    }

    override fun onError(e: FirebaseFirestoreException) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        Log.d("Booking stuff","ViewHolder created")
        return ViewHolder(inflater.inflate(R.layout.item_booking, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), mListener)
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var eventName: TextView = itemView.findViewById(R.id.booking_name)
        var bookingDate: TextView = itemView.findViewById(R.id.booking_date)
        private val datePattern: String = "dd/MM/yyy"
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(datePattern)

        fun bind(snapshot: DocumentSnapshot, listener: OnBookingSelectedListener) {

            val booking: Booking? = snapshot.toObject(Booking::class.java)

            val resources = itemView.resources

            if(booking!= null){
                eventName.text = booking.eventName
                Log.d("Booking Date",booking.timestamp.toString())
                bookingDate.text = simpleDateFormat.format(booking.timestamp!!)
            }
            itemView.setOnClickListener {
                listener.onBookingSelected(snapshot)
            }
        }
    }
}