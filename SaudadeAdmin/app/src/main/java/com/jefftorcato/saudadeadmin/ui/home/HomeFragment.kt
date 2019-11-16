package com.jefftorcato.saudadeadmin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.databinding.FragmentHomeBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(),KodeinAware {

    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel

    private var mToolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this,factory).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        (activity as AppCompatActivity).supportActionBar!!.hide()
        binding.viewmodel = homeViewModel
        mToolbar = binding.root.findViewById(R.id.toolbar)


        //val textView: TextView = root.findViewById(R.id.text_home)
        /*homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

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