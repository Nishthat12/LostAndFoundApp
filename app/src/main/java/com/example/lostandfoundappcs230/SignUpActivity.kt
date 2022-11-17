package com.example.lostandfoundappcs230

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivitySignupactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage

class SignUpActivity : AppCompatActivity() {

    // define the global variable
    // Add button Move to next Activity and previous Activity
    private lateinit var binding: ActivitySignupactivityBinding //binding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var profileUri: Uri? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var profileUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        val emailString = "@iitp.ac.in"
        binding = ActivitySignupactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileUrl = null.toString()

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.btProfile.setOnClickListener {
            selectImage()
        }

        binding.btUpload.setOnClickListener {
            val rollNumber = binding.etRollno.text.toString()
            if (rollNumber.isNotEmpty()) {
                uploadImage(rollNumber)
            } else {
                Toast.makeText(
                    this,
                    "Empty Fields are not allowed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.btRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val rollNumber = binding.etRollno.text.toString()
            val rePassword = binding.etRepeatPassword.text.toString()
            val name = binding.etName.text.toString()
            val contactNumber = binding.etNumber.text.toString()

            if (email.endsWith(emailString)) {
                if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty() && name.isNotEmpty() && rollNumber.isNotEmpty() && contactNumber.isNotEmpty()) {
                    if (password == rePassword) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { authResultTask ->
                                if (authResultTask.isSuccessful) {
                                    val user = User(
                                        name,
                                        rollNumber,
                                        email,
                                        password,
                                        contactNumber,
                                        profileUrl
                                    )
                                    val uid = firebaseAuth.currentUser?.uid
                                    if (uid != null) {
                                        databaseReference.child(uid).setValue(user)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "Registered Successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
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

                                            retrieveAndStoreToken()
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        }?.addOnFailureListener {
                                            Toast.makeText(
                                                this, it.toString(), Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                } else {
                                    Toast.makeText(
                                        this,
                                        authResultTask.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this, "Password is not matching", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Empty Fields are Not Allowed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this, "Use your IITP Webmail address only", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadImage(rollNumber: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storage =
            FirebaseStorage.getInstance().getReference("profileImages/$rollNumber")
        storage.putFile(profileUri!!).addOnSuccessListener {
            Toast.makeText(this@SignUpActivity, "Image uploaded", Toast.LENGTH_SHORT).show()
            storage.downloadUrl.addOnSuccessListener {
                profileUrl = it.toString()
            }
            if (progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener {
            Toast.makeText(this@SignUpActivity, "Failed", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 1)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            profileUri = data?.data!!
            binding.profileImg.setImageURI(profileUri)
        }
    }
}

