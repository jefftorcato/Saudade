package com.jefftorcato.saudadeadmin.ui.eventDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.*

import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.adapter.RatingAdapter
import com.jefftorcato.saudadeadmin.data.models.Rating
import com.jefftorcato.saudadeadmin.databinding.FragmentEventDetailBinding
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class EventDetailFragment : Fragment(),
    View.OnClickListener, EventListener<DocumentSnapshot>,
    RatingDialogFragment.RatingListener,
    KodeinAware {

    companion object {
        fun newInstance() = EventDetailFragment()
    }

    override val kodein by kodein()
    private val factory: EventDetailViewModelFactory by instance()
    private val TAG = "Event Detail"
    val KEY_EVENT_ID = "key_event_id"

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

    private var mFirestore: FirebaseFirestore? = null
    private var mEventRef: DocumentReference? = null
    private var mEventRegistration: ListenerRegistration? = null

    private var mRatingAdapter: RatingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventDetailviewModel = ViewModelProviders.of(this).get(EventDetailViewModel::class.java)
        val binding: FragmentEventDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false)
        binding.viewmodel = eventDetailviewModel
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

        FirebaseFirestore.setLoggingEnabled(true)
        initFirestore()
        initRecyclerView()
        return binding.root
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()

        mEventRef = mFirestore!!.collection("event").document()
        val ratingsQuery: Query = mEventRef!!.collection("ratings")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)

        mRatingAdapter = RatingAdapter(ratingsQuery)
        mRatingsRecycler.layoutManager = LinearLayoutManager(this.context)
        mRatingsRecycler.adapter = mRatingAdapter
        mRatingDialog = RatingDialogFragment()
    }

    fun initRecyclerView() {

    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRating(rating: Rating) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEvent(p0: DocumentSnapshot?, p1: FirebaseFirestoreException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
