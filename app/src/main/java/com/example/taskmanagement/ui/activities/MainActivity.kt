package com.example.taskmanagement.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.taskmanagement.R
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.databinding.ActivityMainBinding
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.SharedPreferences
import io.sentry.Sentry


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TaskManagement)
//        setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        Sentry.captureMessage("testing SDK setup")


        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);

        setupNavigationComponent()

    }
    override fun onResume() {
        super.onResume()
        if (prefs!!.getBoolean("firstrun", true)) {
            addFakeUsersToDatabase()
            prefs!!.edit().putBoolean("firstrun", false).commit()
        }
    }
    private fun addFakeUsersToDatabase() {
        val users = listOf(
            User(0, "Bahram", "1", R.drawable.ic_user_1),
            User(0, "Melika", "2", R.drawable.ic_user_2),
            User(0, "Behruz", "3", R.drawable.ic_user_3),
            User(0, "Mona", "4", R.drawable.ic_user_4),
            User(0, "Ariya", "5", R.drawable.ic_user_5),
            User(0, "Shadi", "6", R.drawable.ic_user_6),
            User(0, "Parsa", "7", R.drawable.ic_user_7),
            User(0, "Parisa", "8", R.drawable.ic_user_8),
        )
        lifecycleScope.launch() {
            users.forEach { viewModel.insertUser(it) }
        }
    }


    private fun setupNavigationComponent() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivityFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom_navigation_view)
        val menu = popupMenu.menu
        activityMainBinding.bottomNavigationView.setupWithNavController(menu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.showAllTasksFragment ->
                    activityMainBinding.bottomNavigationView.visibility = View.GONE
                R.id.createTaskFragment ->
                    activityMainBinding.bottomNavigationView.visibility = View.GONE
                R.id.taskDetailFragment ->
                    activityMainBinding.bottomNavigationView.visibility = View.GONE
                else ->
                    activityMainBinding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}