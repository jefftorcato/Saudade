package com.jefftorcato.saudade.ui.dialog.ratingDialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.jefftorcato.saudade.R
import com.jefftorcato.saudade.data.models.Rating
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class RatingDialogFragment: DialogFragment(), View.OnClickListener {

    companion object{
        val TAG: String = "RatingDialog"
    }

    private var mRatingBar: MaterialRatingBar? = null
    private var mRatingText: EditText? = null

    internal interface RatingListener {

        fun onRating(rating: Rating)

    }

    private var mRatingListener: RatingListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.dialog_rating, container, false)
        mRatingBar = v.findViewById(R.id.event_form_rating)
        mRatingText = v.findViewById(R.id.event_form_text)

        v.findViewById<Button>(R.id.event_form_button).setOnClickListener(this)
        v.findViewById<Button>(R.id.event_form_cancel).setOnClickListener(this)

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is RatingListener) {
            mRatingListener = context
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.event_form_button -> onSubmitClicked(v)
                R.id.event_form_cancel -> onCancelClicked(v)
            }
        }
    }

    fun onSubmitClicked(view: View) {
        val rating = Rating(
            FirebaseAuth.getInstance().currentUser!!,
            mRatingBar?.rating!!.toDouble(),
            mRatingText?.text.toString()
        )

        if (mRatingListener != null) {
            mRatingListener!!.onRating(rating)
        }

        dismiss()
    }

    fun onCancelClicked(view: View) {
        dismiss()
    }
}