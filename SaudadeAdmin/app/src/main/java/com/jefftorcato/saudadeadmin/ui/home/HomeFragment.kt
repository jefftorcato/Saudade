package com.jefftorcato.saudadeadmin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this,factory).get(HomeViewModel::class.java)
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        binding.viewmodel = homeViewModel
        //val textView: TextView = root.findViewById(R.id.text_home)
        /*homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return binding.root
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