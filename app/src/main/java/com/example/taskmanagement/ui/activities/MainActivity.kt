package com.example.taskmanagement.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanagement.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TaskManagement)
        setContentView(R.layout.activity_main)
    }
}