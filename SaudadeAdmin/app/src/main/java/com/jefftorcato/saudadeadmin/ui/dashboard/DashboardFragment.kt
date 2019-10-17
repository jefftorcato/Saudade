package com.jefftorcato.saudadeadmin.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jefftorcato.saudadeadmin.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var spinnerCategory: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        //addItemsToSpinner(root)
        //val categoryRadio:RadioButton = root.findViewById(R.id.radioGroup)
        //onRadioButtonCategoryClicked(categoryRadio)
        return root
    }

    /*private fun addItemsToSpinner(root: View) {
        spinnerCategory = root.findViewById(R.id.planets_spinner)

        val categoryList = ArrayList<String>()
        categoryList.add("Pani")
        categoryList.add("Jeff")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        spinnerCategory.adapter = arrayAdapter

    }*/

    fun onRadioButtonCategoryClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_exhibition ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.radio_tiatr ->
                    if (checked) {
                        // Ninjas rule
                    }
                R.id.radio_activity ->
                    if (checked) {
                        // Ninjas rule
                    }
                R.id.radio_stuff ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }
}