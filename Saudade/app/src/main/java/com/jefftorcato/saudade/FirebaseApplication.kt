package com.jefftorcato.saudade

import android.app.Application
import com.jefftorcato.saudade.data.firebase.FirebaseSource
import com.jefftorcato.saudade.data.repositories.UserRepository
import com.jefftorcato.saudade.ui.auth.AuthViewModelFactory
import com.jefftorcato.saudade.ui.eventDetail.EventDetailViewModelFactory
import com.jefftorcato.saudade.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { EventDetailViewModelFactory(instance()) }

    }
}