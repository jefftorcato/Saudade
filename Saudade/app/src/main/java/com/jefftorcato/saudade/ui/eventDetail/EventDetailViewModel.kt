package com.jefftorcato.saudade.ui.eventDetail

import androidx.lifecycle.ViewModel
import com.jefftorcato.saudade.data.repositories.UserRepository

class EventDetailViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }


}