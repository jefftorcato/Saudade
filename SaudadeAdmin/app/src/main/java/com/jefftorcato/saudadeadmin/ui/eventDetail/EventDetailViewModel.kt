package com.jefftorcato.saudadeadmin.ui.eventDetail

import androidx.lifecycle.ViewModel
import com.jefftorcato.saudadeadmin.data.repositories.UserRepository

class EventDetailViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }


}
