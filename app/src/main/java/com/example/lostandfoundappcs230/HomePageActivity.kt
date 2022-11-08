package com.example.lostandfoundappcs230


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivityHomepageactivityBinding

class HomePageActivity : AppCompatActivity() {
    // define the global variable
    private lateinit var binding: ActivityHomepageactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() //hide the title bar

        binding.PostLostCard.setOnClickListener {
            val intent = Intent(this@HomePageActivity, Post_LostActivity::class.java)
            startActivity(intent)
        }
        binding.PostFoundCard.setOnClickListener {
            val intent = Intent(this, Post_FoundActivity::class.java)
            startActivity(intent)
        }
        binding.FeedLostCard.setOnClickListener {
            val intent = Intent(this, LostFeedActivity::class.java)
            startActivity(intent)
        }
        binding.FeedFoundCard.setOnClickListener {
            val intent = Intent(this, FoundFeedActivity::class.java)
            startActivity(intent)
        }

        binding.profileButton.setOnClickListener {
            val intent = Intent(this, ProfilePageActivity::class.java)
            startActivity(intent)
        }

    }
}