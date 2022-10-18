package com.example.lostandfoundappcs230

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to Activity
    private lateinit var Signup_btn: Button
    private lateinit var Signin_btn: Button

    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the Button
        Signup_btn = findViewById(R.id.signup_btn)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message

        // Add_button add clicklistener
        Signup_btn.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SignUpActivity with the following code.
            val intent = Intent(this, SignUpActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
        Signin_btn = findViewById(R.id.signin_btn)

    }
}
