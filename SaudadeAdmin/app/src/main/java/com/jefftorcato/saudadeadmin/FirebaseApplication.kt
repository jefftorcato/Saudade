package com.jefftorcato.saudadeadmin

import android.app.Application
import com.jefftorcato.saudadeadmin.data.firebase.FirebaseSource
import com.jefftorcato.saudadeadmin.data.repositories.UserRepository
import com.jefftorcato.saudadeadmin.ui.auth.AuthViewModelFactory
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
        //bind() from provider { HomeViewModelFactory(instance()) }
    }
}