package com.jefftorcato.saudade.data.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import java.util.ArrayList
import com.google.firebase.firestore.DocumentSnapshot


abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder>(private var mQuery: Query?) :
    RecyclerView.Adapter<VH>(),EventListener<QuerySnapshot> {

    companion object{
        val TAG: String = "Firestore Adapter"
    }

    private var mRegistration: ListenerRegistration? = null
    private val mSnapshots = ArrayList<DocumentSnapshot>()

    fun startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery!!.addSnapshotListener(this)
        }
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

    override fun onEvent(documentSnapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {

        if(e!=null) {
            Log.w(TAG, "onEvent:Error",e)
            return
        }

        if (documentSnapshots != null) {
            for (change in documentSnapshots.documentChanges) {
                // Snapshot of the changed document
                val snapshot = change.document

                when (change.type) {
                    DocumentChange.Type.ADDED -> onDocumentAdded(change)
                    DocumentChange.Type.MODIFIED -> onDocumentModified(change)
                    DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
                }
            }

            onDatachanged()
        }
    }

    protected fun onDocumentAdded(change: DocumentChange) {
        mSnapshots.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    protected fun onDocumentModified(change: DocumentChange) {
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mSnapshots[change.oldIndex] = change.document
            notifyItemChanged(change.oldIndex)
        } else {
            // Item changed and changed position
            mSnapshots.removeAt(change.oldIndex)
            mSnapshots.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    protected fun onDocumentRemoved(change: DocumentChange) {
        mSnapshots.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

    protected fun getSnapshot(index: Int): DocumentSnapshot {
        return mSnapshots[index]
    }

    protected fun onError(e:FirebaseFirestoreException) {}

    protected fun onDatachanged() {}
}