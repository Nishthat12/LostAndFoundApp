package com.example.lostandfoundappcs230

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.view.View

class MainActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to Activity
    private lateinit var nextActivitybutton: Button

    private lateinit var signin: TextView
    private lateinit var signup: Button
    private lateinit var signin_signup_txt: TextView
    private lateinit var signin_signup_btn: Button
    private lateinit var forgot_password: TextView

    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the Button
        nextActivitybutton = findViewById(R.id.signup)

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message

        // Add_button add clicklistener
        nextActivitybutton.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called SecondActivity with the following code.
            val intent = Intent(this, SecondActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
        signin = findViewById(R.id.signin)
        signup = findViewById(R.id.signup)
        signin_signup_txt = findViewById(R.id.signin_signup_txt)
        signin_signup_btn = findViewById(R.id.signin_signup_btn)
        forgot_password = findViewById(R.id.forgot_password)

        signin.setOnClickListener {
            signin.setTextColor(Color.parseColor("#FFFFFF"))
            signin.setBackgroundColor(Color.parseColor("#FF2729C3"))
            signup.setTextColor(Color.parseColor("#FF2729C3"))
            signin_signup_txt.text = "Sign In"
            signin_signup_btn.text = "Sign In"
            forgot_password.visibility = View.VISIBLE
        }
        signup.setOnClickListener {
            signup.setTextColor(Color.parseColor("#FFFFFF"))
            signup.setBackgroundColor(Color.parseColor("#FF2729C3"))
            signin.setTextColor(Color.parseColor("#FF2729C3"))
            signin_signup_txt.text = "Sign Up"
            signin_signup_btn.text = "Sign Up"
            forgot_password.visibility = View.INVISIBLE
        }
    }
}
