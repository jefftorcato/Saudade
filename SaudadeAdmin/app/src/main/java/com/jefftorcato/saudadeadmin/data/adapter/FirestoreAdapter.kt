package com.jefftorcato.saudadeadmin.data.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.ArrayList

abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH> {

    companion object{
        val TAG: String = "Firestore Adapter"
    }

    private var mRegistration: ListenerRegistration? = null
    private val mSnapshots = ArrayList<DocumentSnapshot>()

    private var mQuery: Query? = null

    constructor(mQuery: Query?) : super() {
        this.mQuery = mQuery
    }

    fun startListening() {
        // TODO(developer): Implement
    }

    fun stopListening() {
        if (mRegistration != null) {
            mRegistration!!.remove()
            mRegistration = null
        }

        mSnapshots.clear()
        notifyDataSetChanged()
    }

    fun setQuery(query: Query) {
        // Stop listening
        stopListening()

        // Clear existing data
        mSnapshots.clear()
        notifyDataSetChanged()

        // Listen to new query
        mQuery = query
        startListening()
    }

    override fun getItemCount(): Int {
        return mSnapshots.size
    }

    protected fun getSnapshot(index: Int): DocumentSnapshot {
        return mSnapshots[index]
    }

    protected fun onError(e:FirebaseFirestoreException) {
        Log.e(TAG,e.message!!)
    }

    protected fun onDatachanged() {}
}