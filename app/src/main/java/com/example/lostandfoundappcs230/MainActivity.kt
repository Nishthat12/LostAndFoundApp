package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var Signup_btn: Button
    private lateinit var Signin_btn: Button
    private lateinit var ForgotPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()  //initialize firebase auth
        Signup_btn = findViewById(R.id.signup_btn)

        // Add_button add clicklistener
        Signup_btn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }

        ForgotPass = findViewById(R.id.forgot_password)
        ForgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        Signin_btn = findViewById(R.id.signin_btn)
        binding.signinBtn.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val verification = firebaseAuth.currentUser?.isEmailVerified
                            if (verification == true) {
                                retrieveAndStoreToken()
                                val intent = Intent(this, HomePageActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun retrieveAndStoreToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val token: String? = it.result
                    val userID = FirebaseAuth.getInstance().currentUser!!.uid
                    FirebaseDatabase.getInstance().getReference("Tokens").child(userID).child("token")
                        .setValue(token)
                }
            }

    }
}
