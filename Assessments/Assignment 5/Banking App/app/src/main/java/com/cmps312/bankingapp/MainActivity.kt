package com.cmps312.bankingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val SIGN_IN_CODE = 300
    private val projectViewModel: AccountViewModel by viewModels()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.accountsListFragment))
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Connect the bottomNavBar with the navController to auto-handle OnNavigationItemSelected
        bottomNavBar.setupWithNavController(navController)

        Firebase.auth.addAuthStateListener {
            if (it.currentUser?.uid == null) {
                showSignIn();
            } else {
                projectViewModel.registerListeners()
                val displayName = it.currentUser!!.displayName ?: "Unknown"
                Toast.makeText(this, "Welcome Mr $displayName", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle Navigate Up event (triggered when clicking the arrow button on the Top App Bar
    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()


    //todo create the sign in
    private fun showSignIn() {
        //providers for the sign in

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.mipmap.ic_launcher)
            .setIsSmartLockEnabled(false)
            .build()
        startActivityForResult(intent, SIGN_IN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = IdpResponse.fromResultIntent(data)
        if (requestCode == SIGN_IN_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            } else {
//                Toast.makeText(
//                    this,
//                    response?.error?.message, Toast.LENGTH_SHORT
//                ).show()
                showSignIn()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMI -> {
                //todo signout the user from the firestore auth
                Firebase.auth.signOut()
                projectViewModel.unRegisterListeners()
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}