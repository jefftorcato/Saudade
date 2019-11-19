package com.jefftorcato.saudade.ui.eventDetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.adapter.RatingAdapter
import com.jefftorcato.saudade.data.models.Event
import com.jefftorcato.saudade.data.models.Rating
import com.jefftorcato.saudade.databinding.ActivityEventdetailBinding
import com.jefftorcato.saudade.ui.dialog.ratingDialog.RatingDialogFragment
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EventDetailActivity : AppCompatActivity(),
    KodeinAware,
    View.OnClickListener,
    RatingDialogFragment.RatingListener,
    EventListener<DocumentSnapshot> {


    override val kodein by kodein()
    private val factory: EventDetailViewModelFactory by instance()

    companion object{
        private val TAG: String = "Event Detail"
        val KEY_EVENT_ID = "key_event_id"
    }

    private lateinit var eventDetailviewModel: EventDetailViewModel

    private lateinit var mImageView: ImageView
    private lateinit var mNameView: TextView
    private lateinit var mRatingIndicator: MaterialRatingBar
    private lateinit var mNumRatingsView: TextView
    private lateinit var mCityView: TextView
    private lateinit var mCategoryView: TextView
    private lateinit var mEmptyView: ViewGroup
    private lateinit var mRatingsRecycler: RecyclerView

    private var mRatingDialog: RatingDialogFragment? = null

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mEventRef: DocumentReference
    private var mEventRegistration: ListenerRegistration? = null

    private var mRatingAdapter: RatingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventdetail)

        val binding: ActivityEventdetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_eventdetail)
        eventDetailviewModel =
            ViewModelProviders.of(this, factory).get(EventDetailViewModel::class.java)

        mImageView = binding.root.findViewById(R.id.event_image)
        mNameView = binding.root.findViewById(R.id.event_name)
        mRatingIndicator = binding.root.findViewById(R.id.event_rating)
        mNumRatingsView = binding.root.findViewById(R.id.event_num_ratings)
        mCityView = binding.root.findViewById(R.id.event_city)
        mCategoryView = binding.root.findViewById(R.id.event_category)
        mEmptyView = binding.root.findViewById(R.id.view_empty_ratings)
        mRatingsRecycler = binding.root.findViewById(R.id.recycler_ratings)


        binding.root.findViewById<ImageView>(R.id.event_button_back).setOnClickListener(this)
        binding.root.findViewById<FloatingActionButton>(R.id.fab_show_rating_dialog)
            .setOnClickListener(this)

        binding.viewmodel = eventDetailviewModel

        val eventId = intent.extras!!.getString(KEY_EVENT_ID)
            ?: throw IllegalArgumentException("Must pass extra $KEY_EVENT_ID")
        FirebaseFirestore.setLoggingEnabled(true)

        // Initialize Firestore and the main RecyclerView
        initFirestore(eventId)
        initRecyclerView()

    }

    fun initFirestore(eventId: String) {
        mFirestore = FirebaseFirestore.getInstance()

        mEventRef = mFirestore.collection("event").document(eventId)
        val ratingsQuery: Query = mEventRef!!.collection("ratings")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)
        mRatingAdapter = RatingAdapter(ratingsQuery)

    }

    fun initRecyclerView() {

        mRatingsRecycler.layoutManager = LinearLayoutManager(this)
        mRatingsRecycler.adapter = mRatingAdapter
        mRatingDialog = RatingDialogFragment()
    }

    override fun onStart() {
        super.onStart()

        mRatingAdapter!!.startListening()
        mEventRegistration = mEventRef!!.addSnapshotListener(this)
    }

    override fun onStop() {
        super.onStop()

        mRatingAdapter!!.stopListening()

        if (mEventRegistration != null) {
            mEventRegistration!!.remove()
            mEventRegistration = null
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.event_button_back -> onBackArrowClicked(v)
            R.id.fab_show_rating_dialog -> onAddRatingClicked(v)
        }
    }

    private fun addRating(eventRef: DocumentReference, rating: Rating): Task<Void> {

        val ratingRef: DocumentReference = eventRef.collection("ratings").document()

        return mFirestore.runTransaction {

            val event: Event = it.get(eventRef).toObject(Event::class.java)!!
             Log.d(TAG,event.getAvgRating().toString())
            val newNumRatings = event.getNumRatings() + 1

            Log.d(TAG,rating.rating.toString())
            val oldRatingTotal = event.getAvgRating() * event.getNumRatings()
            val newAvgRating = (oldRatingTotal + rating.rating) / newNumRatings

            Log.d(TAG,newAvgRating.toString())

            event.setNumRatings(newNumRatings)
            event.setAvgRating(newAvgRating)

            it.set(eventRef,event)
            it.set(ratingRef,rating)
            null
        }
    }

    override fun onEvent(snapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (e != null) {
            Log.w(TAG, "Event:onEvent", e)
            return
        }

        onEventLoaded(snapshot!!.toObject(Event::class.java)!!)
    }

    private fun onEventLoaded(event: Event) {
        mNameView.text = event.getName()
        mRatingIndicator.rating = event.getAvgRating().toFloat()
        mNumRatingsView.text = getString(R.string.fmt_num_ratings, event.getNumRatings())
        mCityView.text = event.getCity()
        mCategoryView.text = event.getCategory()

        // Background image
        Glide.with(mImageView.context)
            .load(event.getPhoto())
            .into(mImageView)
    }

    fun onBackArrowClicked(view: View) {
        onBackPressed()
    }

    fun onAddRatingClicked(view: View) {
        mRatingDialog!!.show(supportFragmentManager, RatingDialogFragment.TAG)
    }

    override fun onRating(rating: Rating) {
        // In a transaction, add the new rating and update the aggregate totals
        addRating(mEventRef, rating)
            .addOnSuccessListener(this) {
                Log.d(TAG, "Rating added")

                // Hide keyboard and scroll to top
                hideKeyboard()
                mRatingsRecycler.smoothScrollToPosition(0)
            }
            .addOnFailureListener(this) { e ->
                Log.w(TAG, "Add rating failed", e)

                // Show failure message and hide keyboard
                hideKeyboard()
                Snackbar.make(
                    findViewById(android.R.id.content), "Failed to add rating",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}