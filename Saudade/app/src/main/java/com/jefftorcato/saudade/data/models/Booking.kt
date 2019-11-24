package com.jefftorcato.saudade.data.models

import android.text.TextUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Booking {
    var userId: String? = null
    var userName: String? = null
    @ServerTimestamp
    var timestamp: Date? = null
    var eventName: String? = null
    var eventCategory: String? = null

    constructor() {}

    constructor(user: FirebaseUser,event: Event) {
        this.userId = user.uid
        this.userName = user.displayName
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.email
        }

        this.eventName = event.getName()
        this.eventCategory = event.getCategory()
    }
}