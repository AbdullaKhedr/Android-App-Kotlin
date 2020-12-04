package com.cmps312.learningpackageeditorapp.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.cmps312.learningpackageeditorapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            if (dataFilled()) {
                doRegistration()
            }
        }

        signInTv.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun dataFilled(): Boolean {
        val firstName = firstNameEdt.text.toString().trim()
        val lastName = lastNameEdt.text.toString().trim()
        val email = emailEdt.text.toString().trim()
        val phone = phoneEdt.text.toString().trim()
        val pass1 = password1Edt.text.toString().trim()
        val pass2 = password2Edt.text.toString().trim()

        return if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
            context.toast("Please fill all the requirements")
            false
        } else if (pass1 != pass2) {
            context.toast("Sorry, Password does not match")
            false
        } else
            true
    }

    private fun doRegistration() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Registering...")
        progressDialog.show()

        val email = emailEdt.text.toString().trim()
        val pass = password1Edt.text.toString().trim()
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val newUser = User(
                        firebaseAuth.currentUser!!.uid,
                        firstNameEdt.text.toString().trim(),
                        lastNameEdt.text.toString().trim(),
                        emailEdt.text.toString().trim(),
                        phoneEdt.text.toString().trim(),
                        password1Edt.text.toString().trim(),
                        "Teacher"
                    )
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(firebaseAuth.currentUser!!.uid)
                        .setValue(newUser)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                progressDialog.dismiss()
                                context.toast("Registration done successfully")
                                activity?.onBackPressed()
                            } else {
                                progressDialog.dismiss()
                                context.toast("Registration failed")
                            }
                        }

                } else {
                    progressDialog.dismiss()
                    context.toast("Can't create the user")
                }
            }
    }
}