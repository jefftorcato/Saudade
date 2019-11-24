package com.jefftorcato.saudade.ui.bookings

import androidx.lifecycle.ViewModel
import com.jefftorcato.saudade.data.repositories.UserRepository

class BookingsViewModel(
    private val repository: UserRepository
) : ViewModel(){

    val user by lazy {
        repository.currentUser()
    }

    fun logout(){
        repository.logout()
        //view.context.startLoginActivity()
    }
}