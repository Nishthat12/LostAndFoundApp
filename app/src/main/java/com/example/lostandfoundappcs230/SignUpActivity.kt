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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var binding: ActivitySignupactivityBinding
    private lateinit var database : DatabaseReference
    private lateinit var signup_btn: Button
//    private lateinit var previous_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_signupactivity)
        supportActionBar?.hide(); //hide the title bar

        // by ID we can use each component which id is assign in xml
        // file use findViewById() to get the both Button and textview
        signup_btn = findViewById(R.id.bt_register)

        signup_btn.setOnClickListener {
            // Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining
            // the components you are targeting. Intent to start an activity called ThirdActivity with the following code.
            val intent = Intent(this, HomePageActivity::class.java)
            // start the activity connect to the specified class
            startActivity(intent)
        }
        binding.btRegister.setOnClickListener{
            val Name = binding.name.text.toString()
            val RollNumber = binding.etRollno.text.toString()
            val Email = binding.etEmail.text.toString()
            val Password = binding.etPassword.text.toString()
            val ContactNumber = binding.etNumber.text.toString()

            database = FirebaseDatabase.getInstance().getReference("User")

            val User = User(Name,RollNumber,Email, Password,ContactNumber)
            database.child(Email).setValue(User).addOnSuccessListener {
                binding.name.text.clear()
                binding.etRollno.text.clear()
                binding.etEmail.text.clear()
                binding.etPassword.text.clear()
                binding.etNumber.text.clear()

                Toast.makeText(this, "Succesfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed",Toast.LENGTH_SHORT).show()
            }
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

