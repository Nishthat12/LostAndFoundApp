package com.example.lostandfoundappcs230

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lostandfoundappcs230.databinding.ActivityPostLostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Post_LostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostLostBinding
    private lateinit var database : DatabaseReference

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereLost: EditText
    private lateinit var submitBtn: Button


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityPostLostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        readData(userID)


        etName = findViewById(R.id.post_lost_name)
        etPhoneNumber = findViewById(R.id.post_lost_number)
        etMessage = findViewById(R.id.post_lost_message)
        etWhereLost = findViewById(R.id.post_lost_where)
        submitBtn = findViewById(R.id.Btn_post_lost_submit)

        submitBtn.setOnClickListener {

            val sName = etName.text.toString().trim()
            val sPhoneNumber = etPhoneNumber.text.toString().trim()
            val sMessage = etMessage.text.toString().trim()
            val sWhereLost = etWhereLost.text.toString().trim()
            val userMap = hashMapOf(
                "name" to sName,
                "phoneNumber" to sPhoneNumber,
                "message" to sMessage,
                "whereLost" to sWhereLost
            )

            db.collection("user").document(userID).set(userMap)
                .addOnSuccessListener {
                Toast.makeText(this, "Successfully Posted", Toast.LENGTH_SHORT).show()
                etName.text.clear()
                etPhoneNumber.text.clear()
                etMessage.text.clear()
                etWhereLost.text.clear()
            }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun readData(userID: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userID).get().addOnSuccessListener {

            val name = it.child("name").value
            val number = it.child("contactNumber").value
            etName.text = name.toString().toEditable()
            etPhoneNumber.text = number.toString().toEditable()

        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}