package com.example.lostandfoundappcs230


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity() {
    // define the global variable
    private lateinit var postlost: Button
    private lateinit var postfound: Button
    private lateinit var feedlost: Button
    private lateinit var feedfound: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepageactivity)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the Button and textview.
        postlost = findViewById(R.id.PostLostCard)
        postfound = findViewById(R.id.PostFoundCard)
        feedlost = findViewById(R.id.FeedLostCard)
        feedfound = findViewById(R.id.FeedFoundCard)

        postlost.setOnClickListener{
            val intent = Intent(this, Lost_FoundActivity::class.java)
            startActivity(intent)
        }
        postfound.setOnClickListener{
            val intent = Intent(this, Lost_FoundActivity::class.java)
            startActivity(intent)
        }
        feedlost.setOnClickListener{
            val intent = Intent(this, LostFeedActivity::class.java)
            startActivity(intent)
        }
        feedfound.setOnClickListener{
            val intent = Intent(this, FoundFeedActivity::class.java)
            startActivity(intent)
        }
    }
}