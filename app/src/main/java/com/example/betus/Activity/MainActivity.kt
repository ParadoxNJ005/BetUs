package com.example.betus.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.betus.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
//                    loadFragment(home_fragment())
                    true
                }
                R.id.message -> {
//                    loadFragment(chat_fragment())
                    true
                }
                R.id.settings -> {
//                    loadFragment(setting_fragment())
                    true
                }
                R.id.about ->{
 //                   loadFragment(About_Fragment())
                    true
                }
                else-> {
 //                   loadFragment(setting_fragment())
                    true
                }

            }
        }
    }
//    private  fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container,fragment)
//        transaction.commit()
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}