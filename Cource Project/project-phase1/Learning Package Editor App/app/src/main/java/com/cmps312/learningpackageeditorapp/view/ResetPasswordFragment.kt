package com.cmps312.learningpackageeditorapp.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        resetPassBtn.setOnClickListener {
            if (dataFilled()) {
                val progressDialog = ProgressDialog(requireContext())
                progressDialog.setMessage("Sending email...")
                progressDialog.show()

                val email = userEmail.text.toString().trim()
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        progressDialog.dismiss()
                        context.toast("Email sent successfully!")
                        activity?.onBackPressed()
                    } else {
                        progressDialog.dismiss()
                        context.toast("Sending email failed!")
                    }
                }
            }
        }
    }

    private fun dataFilled(): Boolean {
        val email = userEmail.text.toString().trim()
        return if (email.isEmpty()) {
            context.toast("Please enter your email!")
            false
        } else
            true
    }
}
