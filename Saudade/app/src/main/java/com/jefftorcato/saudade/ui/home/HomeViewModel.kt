package com.jefftorcato.saudade.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.jefftorcato.saudade.data.repositories.UserRepository
import com.jefftorcato.saudade.ui.dialog.filterDialog.Filters
import com.jefftorcato.saudade.utils.startLoginActivity

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    private var mFilters: Filters? = null

    fun getFilters():Filters? {return mFilters}

    fun setFilters(mFilters:Filters?) { this.mFilters = mFilters}

    fun logout(){
        repository.logout()
        //view.context.startLoginActivity()
    }
}