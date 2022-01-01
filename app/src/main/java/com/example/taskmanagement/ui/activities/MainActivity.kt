package com.example.taskmanagement.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TaskManagement)
//        setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupNavigationComponent()
    }

    private fun setupNavigationComponent() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivityFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.menu_bottom_navigation_view)
        val menu = popupMenu.menu
        activityMainBinding.bottomNavigationView.setupWithNavController(menu,navController)


    }
}