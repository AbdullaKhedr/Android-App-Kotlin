package cmps312.lab.bankingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration =  AppBarConfiguration(setOf(R.id.transferListFragment, R.id.transferFragment))
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Connect the bottomNavBar with the navController to auto-handle OnNavigationItemSelected
        bottomNavBar.setupWithNavController(navController)
    }

    // Handle Navigate Up event (triggered when clicking the arrow button on the Top App Bar
    override fun onSupportNavigateUp() =  navController.navigateUp() || super.onSupportNavigateUp()
}
