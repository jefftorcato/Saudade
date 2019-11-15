package com.jefftorcato.saudadeadmin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jefftorcato.saudadeadmin.R
import com.jefftorcato.saudadeadmin.databinding.FragmentNotificationsBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class NotificationsFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory : NotificationsViewModelFactory by instance()
    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this,factory).get(NotificationsViewModel::class.java)
        val binding :FragmentNotificationsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_notifications,container,false)
        binding.viewmodel = notificationsViewModel
        val textView: TextView = binding.root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return binding.root
    }
}