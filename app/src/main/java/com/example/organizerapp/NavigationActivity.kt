package com.example.organizerapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.organizerapp.databinding.ActivityNavigationBinding
import com.example.organizerapp.ui.user.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

/**
    Navigation activity is the basic activity that takes care of the navigation
    between the three main fragment: DailyTasksFragment, MyListsFragment and
    TomatoStatsFragment. In addition, connects the toolbar and manages it's
    functionalities, connects the user id with it's viewModel and handles log out action.
 */
class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var mUserViewModel: UserViewModel

    /**
     * Overwritten OnCreate method that takes care of basic binding initialization as well as
     * userViewModel connection, app bar configuration and navigation controller set up.
     * Also sets the fragments xml files as top destinations for the navigation functionality.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarNavigation.toolbar)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_daily_tasks, R.id.nav_my_list, R.id.nav_tomato_stats
            ), drawerLayout
        )
        //to automatically update toolbars header
        setupActionBarWithNavController(navController, appBarConfiguration)
        //to update fragment when menu item selected
        navView.setupWithNavController(navController)

        showToast("Welcome " + mUserViewModel.getUserById(auth.currentUser.uid)?.username.toString() + "!")
    }

    fun showToast(toast: String?) {
        runOnUiThread {
            Toast.makeText(this@NavigationActivity, toast, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *  Function to connect the options menu to toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        auth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    /**
     * Function to navigate back to host fragment when back button is pressed
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}