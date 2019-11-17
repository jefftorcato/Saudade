package com.jefftorcato.saudadeadmin.ui.home

import android.os.Bundle
import android.os.RecoverySystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.data.adapter.EventAdapter
import com.jefftorcato.saudadeadmin.databinding.FragmentHomeBinding
import com.jefftorcato.saudadeadmin.ui.dialog.FilterDialogFragment
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
        private val LIMIT: Int = 50
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
        (activity as AppCompatActivity).supportActionBar!!.hide()

        binding.viewmodel = homeViewModel
        mToolbar = binding.root.findViewById(R.id.toolbar)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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