package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivityUpdatePassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdatePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdatePassBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var oldPassword: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val user = auth.currentUser
        val userID = user!!.uid
        readData(userID)

        binding.BtnUpdatePassSubmit.setOnClickListener {
            val curpassword = binding.currentPassword.text.toString()
            val password = binding.newPassword.text.toString()
            val rePassword = binding.confirmNewPassword.text.toString()

            if (curpassword.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()){
                if (password == rePassword){
                    if (curpassword == oldPassword){
                        user.updatePassword(password).addOnSuccessListener {
                            databaseReference.child(userID).child("password").setValue(password)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Password Updated", Toast.LENGTH_SHORT).show()
                                    binding.currentPassword.text.clear()
                                    binding.newPassword.text.clear()
                                    binding.confirmNewPassword.text.clear()

                                    val intent = Intent(this, ProfilePageActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                                }
                        }.addOnFailureListener {
                            Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Fill all Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(userID:String) {
        databaseReference.child(userID).get().addOnSuccessListener {
            oldPassword = it.child("password").value.toString()
        }
    }
}