package com.cmps312.learningpackageeditorapp.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val MY_PREFERENCES = "rememberMePrefs"
    private val REMEMBER_ME_CHECK_BOX = "rememberMeCheckBoxState"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (rememberMeStateLoad()) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                findNavController().navigate(R.id.action_loginFragment_to_allPackagesListFragment)
            }
        }

        forgotPasswordTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }

        loginBtn.setOnClickListener {
            val email = userMailEdt.text.toString().trim()
            val pass = userPasswordEdt.text.toString().trim()
            if (dataFilled())
                validatingDataAndLogin(email, pass)
        }

        registerTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun dataFilled(): Boolean {
        val email = userMailEdt.text.toString().trim()
        val pass = userPasswordEdt.text.toString().trim()
        return if (email.isEmpty() || pass.isEmpty()) {
            context.toast("Email or Password is Empty!")
            false
        } else
            true
    }

    private fun validatingDataAndLogin(email: String, pass: String) {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Signing in...")
        progressDialog.show()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progressDialog.dismiss()
                    context.toast("Login successful!")
                    rememberMeStateSave()
                    findNavController().navigate(R.id.action_loginFragment_to_allPackagesListFragment)
                } else {
                    progressDialog.dismiss()
                    context.toast("Login failed!")
                }
            }
    }

    private fun rememberMeStateSave() {
        val sharedPref =
            this.activity?.getSharedPreferences(MY_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
        sharedPref?.edit()?.putBoolean(REMEMBER_ME_CHECK_BOX, rememberMeCb.isChecked)?.apply()
    }

    private fun rememberMeStateLoad(): Boolean {
        val sharedPref =
            this.activity?.getSharedPreferences(MY_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
        return sharedPref?.getBoolean(REMEMBER_ME_CHECK_BOX, false) ?: false
    }
}