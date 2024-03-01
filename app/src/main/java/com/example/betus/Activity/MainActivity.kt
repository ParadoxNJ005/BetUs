package com.example.betus.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.betus.Fragment.BetFragment
import com.example.betus.Fragment.HomeFragment
import com.example.betus.Fragment.LeaderBoard
import com.example.betus.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.Betus -> {
                    loadFragment(BetFragment())
                    true
                }

                R.id.LeaderBoard -> {
                    loadFragment(LeaderBoard())
                    true
                }
                else -> {
                    loadFragment(BetFragment())
                    true
                }
            }
        }

        // Set the default fragment
        if (savedInstanceState == null) {
            loadFragment(BetFragment())
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
