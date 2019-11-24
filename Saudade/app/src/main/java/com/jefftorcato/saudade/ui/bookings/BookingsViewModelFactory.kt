package com.jefftorcato.saudade.ui.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jefftorcato.saudade.data.repositories.UserRepository


@Suppress("UNCHECKED_CAST")
class BookingsViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookingsViewModel(repository) as T
    }

}