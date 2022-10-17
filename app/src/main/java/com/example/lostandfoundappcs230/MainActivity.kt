package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // define the global variable
    private lateinit var question1: TextView
    // Add button Move to Activity
    private lateinit var nextActivitybutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the Button
        nextActivitybutton = findViewById(R.id.first_activity_button)
        question1 = findViewById(R.id.question1_id)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        question1.text = "Q1 - How to pass the data between activities in Android? Ans - Intent".trimIndent()

        // Add_button add clicklistener
        nextActivitybutton.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SecondActivity with the following code.
            val intent = Intent(this, SecondActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
    }
}
