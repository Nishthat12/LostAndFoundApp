package com.example.lostandfoundappcs230

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class FoundFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_found_feed)
    }
}