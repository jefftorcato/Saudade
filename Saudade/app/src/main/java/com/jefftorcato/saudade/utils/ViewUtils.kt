package com.jefftorcato.saudade.utils

import android.content.Context
import android.content.Intent
import com.jefftorcato.saudade.ui.auth.LoginActivity
import com.jefftorcato.saudade.ui.eventDetail.EventDetailActivity
import com.jefftorcato.saudade.ui.home.HomeActivity

fun Context.startHomeActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startEventDetailsActivity() =
    Intent(this,EventDetailActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }