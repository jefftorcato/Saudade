package com.jefftorcato.saudadeadmin.ui.home

import android.os.Bundle
import android.os.RecoverySystem
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.adapter.EventAdapter
import com.jefftorcato.saudadeadmin.databinding.FragmentHomeBinding
import com.jefftorcato.saudadeadmin.ui.dialog.FilterDialogFragment
import com.jefftorcato.saudadeadmin.ui.dialog.Filters
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(),
    KodeinAware,
    View.OnClickListener,
    FilterDialogFragment.FilterListener,
    EventAdapter.OnEventSelectedListener {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel

    private var mToolbar: Toolbar? = null
    private lateinit var mCurrentSearchView: TextView
    private lateinit var mCurrentSortByView: TextView
    private lateinit var mEventsRecycler: RecyclerView
    private lateinit var mEmptyView: ViewGroup

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var mQuery: Query

    private lateinit var mFilterDialog: FilterDialogFragment
    private lateinit var mAdapter: EventAdapter

    companion object {
        private val TAG: String = "MainActivity"
        private const val LIMIT: Long = 50
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //(activity as AppCompatActivity).supportActionBar!!.hide()
        binding.viewmodel = homeViewModel
        //mToolbar = binding.root.findViewById(R.id.toolbar)
        setHasOptionsMenu(true)
        mCurrentSearchView = binding.root.findViewById(R.id.text_current_search)
        mCurrentSortByView = binding.root.findViewById(R.id.text_current_sort_by)
        mEventsRecycler = binding.root.findViewById(R.id.recycler_events)
        mEmptyView = binding.root.findViewById(R.id.view_empty)

        binding.root.findViewById<CardView>(R.id.filter_bar).setOnClickListener(this)
        binding.root.findViewById<ImageView>(R.id.button_clear_filter).setOnClickListener(this)

        FirebaseFirestore.setLoggingEnabled(true)

        initFirestore()
        initRecyclerView()
        mFilterDialog = FilterDialogFragment()
        return binding.root
    }

    fun initFirestore() {
        mFirestore = FirebaseFirestore.getInstance()

        // Get the 50 highest rated restaurants
        mQuery = mFirestore.collection("event")
            .orderBy("avgRating", Query.Direction.DESCENDING)
            .limit(LIMIT)
    }

    fun initRecyclerView() {
        if (mQuery == null) {
            Log.w(TAG, "No query to initialize RecyclerView")
        }

        mAdapter = EventAdapter(mQuery, this)
        // TODO(developer): Implementation of onDataChanged here and even onError
        mEventsRecycler.layoutManager = LinearLayoutManager(this.context)
        mEventsRecycler.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()

        onFilter(homeViewModel.getFilters())

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

            mCurrentSearchView.text = Html.fromHtml(filters.getSearchDescription())
            mCurrentSortByView.text = filters.getOrderDescription(this.context!!)
        }

        homeViewModel.setFilters(filters)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.filter_bar -> onFilterClicked()
            R.id.button_clear_filter -> onClearFilterClicked()
        }
    }

    override fun onEventSelected(event: DocumentSnapshot) {

    }

    fun onFilterClicked() {
        // Show the dialog containing filter options
        //mFilterDialog.show(,FilterDialogFragment.TAG)

        mFilterDialog.show(fragmentManager!!, FilterDialogFragment.TAG)
    }

    fun onClearFilterClicked() {
        mFilterDialog.resetFilters()

        onFilter(Filters.getDefault())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dropdown, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_sign_out -> {
                homeViewModel.logout(view!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*fun downloadEventData(){
        val docRef = db.collection("event")
            .whereEqualTo("artist","NE35cOA1VSQLU8n6guC81f1LXmE3")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }*/
}