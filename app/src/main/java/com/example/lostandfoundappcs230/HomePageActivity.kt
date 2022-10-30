package com.example.lostandfoundappcs230


import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lostandfoundappcs230.databinding.ActivityHomepageactivityBinding

class HomePageActivity : AppCompatActivity() {
    // define the global variable
    private lateinit var binding: ActivityHomepageactivityBinding

    private lateinit var postlost: Button
    private lateinit var postfound: Button
    private lateinit var feedlost: Button
    private lateinit var feedfound: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomePage())
        supportActionBar?.hide(); //hide the title bar

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_menu_item -> replaceFragment(HomePage())
                R.id.profile_menu_item -> replaceFragment(ProfilePage())

                else ->{

                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}