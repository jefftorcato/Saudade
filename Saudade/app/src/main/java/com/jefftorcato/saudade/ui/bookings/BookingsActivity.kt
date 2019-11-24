package com.jefftorcato.saudade.ui.bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.adapter.BookingAdapter
import com.jefftorcato.saudade.databinding.ActivityBookingsBinding
import com.jefftorcato.saudade.ui.auth.LoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BookingsActivity : AppCompatActivity(),
        KodeinAware,
        BookingAdapter.OnBookingSelectedListener {

    override val kodein by kodein()
    private val factory: BookingsViewModelFactory by instance()

    companion object {
        private val TAG: String = "Booking List Activity"
    }

    private lateinit var bookingViewModel: BookingsViewModel
    private lateinit var mBookingsRecycler: RecyclerView
    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query
    private lateinit var mBookingAdapter: BookingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)

        val binding: ActivityBookingsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_bookings)
        bookingViewModel = ViewModelProviders.of(this, factory).get(BookingsViewModel::class.java)

        mBookingsRecycler = binding.root.findViewById(R.id.recycler_bookings)
        binding.viewmodel = bookingViewModel
        FirebaseFirestore.setLoggingEnabled(true)
        initFirestore()
        initRecyclerView()
    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        mQuery = mFirestore.collection("bookings").whereEqualTo("userId", FirebaseAuth.getInstance().currentUser!!.uid)
    }

    private fun initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView")
        }
        Log.d(TAG,mQuery.toString())
        mBookingAdapter = BookingAdapter(mQuery, this)
        mBookingsRecycler.layoutManager = LinearLayoutManager(this)
        mBookingsRecycler.adapter = mBookingAdapter
    }

    override fun onBookingSelected(booking: DocumentSnapshot) {

    }

    override fun onDataChange() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dropdown, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                bookingViewModel.logout()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            R.id.menu_bookings -> {
                Intent(this, BookingsActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        if (mBookingAdapter != null) {
            mBookingAdapter.stopListening()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mBookingAdapter != null) {
            mBookingAdapter.startListening()
        }
    }


}
