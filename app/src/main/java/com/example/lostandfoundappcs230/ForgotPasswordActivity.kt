package com.example.lostandfoundappcs230

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etMail: EditText
    private lateinit var BtResetPass: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etMail = findViewById(R.id.forgot_email)
        BtResetPass = findViewById(R.id.Btn_reset_pass)
        auth = FirebaseAuth.getInstance()

        BtResetPass.setOnClickListener {
            val mail = etMail.text.toString()
            auth.sendPasswordResetEmail(mail).addOnSuccessListener {
                Toast.makeText(this, "Check your mail!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}