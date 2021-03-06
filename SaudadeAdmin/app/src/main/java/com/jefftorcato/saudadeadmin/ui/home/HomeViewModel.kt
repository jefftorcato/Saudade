package com.jefftorcato.saudadeadmin.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jefftorcato.saudadeadmin.data.repositories.UserRepository
import com.jefftorcato.saudadeadmin.ui.dialog.Filters
import com.jefftorcato.saudadeadmin.utils.startLoginActivity

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    private var mFilters: Filters? = null

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }

    fun getFilters():Filters? {return mFilters}

    fun setFilters(mFilters:Filters?) { this.mFilters = mFilters}
}