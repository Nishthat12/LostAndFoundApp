package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivitySignupactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var binding: ActivitySignupactivityBinding //binding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        val emailString = "@iitp.ac.in"
        binding = ActivitySignupactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.btRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repassword = binding.etRepeatPassword.text.toString()
            val name = binding.etName.text.toString()
            val rollNumber = binding.etRollno.text.toString()
            val contactNumber = binding.etNumber.text.toString()

            val user = User(name, rollNumber, email, password, contactNumber)

            if (email.endsWith(emailString)) {
                if (email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()) {
                    if (password == repassword) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val uid = firebaseAuth.currentUser?.uid

                                    if (uid != null) {
                                        databaseReference.child(uid).setValue(user)
                                            .addOnCompleteListener {

                                                if (it.isSuccessful) {

                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Failed to Register",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }
                                    firebaseAuth.currentUser?.sendEmailVerification()
                                        ?.addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Please verify your email!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val intent =
                                                Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        }
                                        ?.addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                it.toString(),
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                } else {
                                    Toast.makeText(
                                        this,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Password is not matching",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Use your IITP Webmail address only",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
//        }
        }
    }
}

