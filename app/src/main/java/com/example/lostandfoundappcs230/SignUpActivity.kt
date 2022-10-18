package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var signup_btn: Button
//    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the both Button and textview
        signup_btn = findViewById(R.id.bt_register)

        signup_btn.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called ThirdActivity with the following code.
            val intent = Intent(this, ThirdActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

//        // Add_button add clicklistener
//        previous_button.setOnClickListener {
//            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
//            // the components you are targeting. Intent to start an activity called oneActivity with the following code
//            val intent = Intent(this, MainActivity::class.java)
//            // start the activity connect to the specified class
//            startActivity(intent)
//        }
    }
}

