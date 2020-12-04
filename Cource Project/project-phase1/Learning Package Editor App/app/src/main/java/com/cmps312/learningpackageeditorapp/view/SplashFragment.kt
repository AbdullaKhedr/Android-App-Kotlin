package com.cmps312.learningpackageeditorapp.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmps312.learningpackageeditorapp.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val splashTime = 3000 //This is 2 second

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Code to start timer and take action after the timer ends
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_startFragment)
        }, splashTime.toLong())
    }
}