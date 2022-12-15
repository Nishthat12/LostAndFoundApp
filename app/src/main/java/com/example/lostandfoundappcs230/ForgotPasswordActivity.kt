package com.example.lostandfoundappcs230

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etMail: EditText
    private lateinit var etPass: EditText
    private lateinit var btResetPass: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        etMail = findViewById(R.id.forgot_email)
        etPass = findViewById(R.id.forgot_pass_password)
//        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        btResetPass = findViewById(R.id.Btn_reset_pass)
        auth = FirebaseAuth.getInstance()

        btResetPass.setOnClickListener {
//            FirebaseAuth.getInstance().currentUser!!.updatePassword(etPass.text.toString())
//                .addOnSuccessListener {
////                    databaseReference.child(userID).child("password")
////                        .setValue(etPass.text.toString())
//                }
            val mail = etMail.text.toString()
            auth.sendPasswordResetEmail(mail).addOnSuccessListener {
                Toast.makeText(this, "Check your mail!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}