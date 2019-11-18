package com.jefftorcato.saudade.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.adapter.EventAdapter
import com.jefftorcato.saudade.databinding.ActivityHomeBinding
import com.jefftorcato.saudade.ui.auth.LoginActivity
import com.jefftorcato.saudade.ui.dialog.filterDialog.FilterDialogFragment
import com.jefftorcato.saudade.ui.dialog.filterDialog.Filters
import com.jefftorcato.saudade.ui.eventDetail.EventDetailActivity
import com.jefftorcato.saudade.utils.startLoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(),
    KodeinAware,
    View.OnClickListener,
    EventAdapter.OnEventSelectedListener,
    FilterDialogFragment.FilterListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()

    companion object {
        private val TAG: String = "MainActivity"
        private const val LIMIT: Long = 50
    }

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mCurrentSearchView: TextView
    private lateinit var mCurrentSortByView: TextView
    private lateinit var mEventsRecycler: RecyclerView
    private lateinit var mEmptyView: ViewGroup

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query

    private lateinit var mFilterDialog: FilterDialogFragment
    private lateinit var mAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //mToolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(mToolbar)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        mCurrentSearchView = binding.root.findViewById(R.id.text_current_search)
        mCurrentSortByView = binding.root.findViewById(R.id.text_current_sort_by)
        mEventsRecycler = binding.root.findViewById(R.id.recycler_events)
        mEmptyView = binding.root.findViewById(R.id.view_empty)

        binding.root.findViewById<CardView>(R.id.filter_bar).setOnClickListener(this)
        binding.root.findViewById<ImageView>(R.id.button_clear_filter).setOnClickListener(this)
        binding.viewmodel = homeViewModel

        FirebaseFirestore.setLoggingEnabled(true)

        // Initialize Firestore and the main RecyclerView
        initFirestore()
        initRecyclerView()
        mFilterDialog = FilterDialogFragment()

    }

    private fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()
        mQuery = mFirestore.collection("event")
            .orderBy("avgRating", Query.Direction.DESCENDING)
            .limit(LIMIT)
    }

    private fun initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView")
        }

        mAdapter = EventAdapter(mQuery, this)
        mEventsRecycler.layoutManager = LinearLayoutManager(this)
        mEventsRecycler.adapter = mAdapter
    }

    fun onFilterClicked() {
        mFilterDialog.show(supportFragmentManager, FilterDialogFragment.TAG)
    }

    fun onClearFilterClicked() {
        mFilterDialog.resetFilters()

        onFilter(Filters.getDefault())
    }

    override fun onStart() {
        super.onStart()

        // Apply filters
        onFilter(homeViewModel.getFilters())

        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

    override fun onEventSelected(event: DocumentSnapshot) {
        Log.d(TAG,event.id)
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra(EventDetailActivity.KEY_EVENT_ID, event.id)

        startActivity(intent)
        /*Intent(this, EventDetailActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }*/
    }

    override fun onFilter(filters: Filters?) {
        var query: Query = mFirestore.collection("event")

        if (filters != null) {
            if (filters.hasCategory()) {
                query = query.whereEqualTo("category", filters.getCategory())
            }

            if (filters.hasCity()) {
                query = query.whereEqualTo("city", filters.getCity())
            }

            if (filters.hasSortBy()) {
                query = query.orderBy(filters.getSortBy()!!, filters.getSortDirection()!!)
            }

            query = query.limit(LIMIT)

            mQuery = query
            mAdapter.setQuery(query)

            mCurrentSearchView.text = HtmlCompat.fromHtml(
                filters.getSearchDescription(this),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            mCurrentSortByView.text = filters.getOrderDescription(this)
        }

        homeViewModel.setFilters(filters)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.filter_bar -> onFilterClicked()
            R.id.button_clear_filter -> onClearFilterClicked()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dropdown, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                homeViewModel.logout()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }


}