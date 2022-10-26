package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivityMainBinding
import com.example.lostandfoundappcs230.databinding.ActivitySignupactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var binding: ActivitySignupactivityBinding //binding
    private lateinit var database: DatabaseReference
    private lateinit var signup_btn: Button
    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_signupactivity)
        supportActionBar?.hide() //hide the title bar

        binding = ActivitySignupactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repassword = binding.etRepeatPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()) {
                if (password == repassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the both Button and textview
        signup_btn = findViewById(R.id.bt_register)

//        signup_btn.setOnClickListener {
//            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
//            // the components you are targeting. Intent to start an activity called ThirdActivity with the following code.
//            val intent = Intent(this, HomePageActivity::class.java)
//            // start the activity connect to the specified class
//            startActivity(intent)
//        }
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

