package com.example.lostandfoundappcs230

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.lostandfoundappcs230.databinding.ActivityProfilePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ProfilePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth

    private lateinit var profile_name: TextView
    private lateinit var profile_rollno: TextView
    private lateinit var profile_number: TextView
    private lateinit var profile_email: TextView
    private lateinit var profile_img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser!!.uid
        readData(userID)

        profile_name = findViewById(R.id.user_profile_name)
        profile_rollno = findViewById(R.id.user_rollno)
        profile_number = findViewById(R.id.user_number)
        profile_email = findViewById(R.id.user_email)
        profile_img = findViewById(R.id.profile_img_profile)

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        binding.myPosts.setOnClickListener {
            val intent = Intent(this, MyLostPostsActivity::class.java)
            startActivity(intent)
        }

        binding.updatePass.setOnClickListener {
            val intent = Intent(this, UpdatePassActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            clearToken(userID)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clearToken(userID: String){
        FirebaseDatabase.getInstance().getReference("tokens").child(userID)
            .removeValue()

    }

    private fun readData(userID:String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userID).get().addOnSuccessListener {

            val name = it.child("name").value
            val number = it.child("contactNumber").value
            val image = it.child("profileUrl").value
            val rollno = it.child("rollNumber").value
            val email = it.child("email").value
            if (image != null.toString()){
                Glide.with(this)
                    .load(image).into(profile_img)
            }
            profile_name.text = name.toString()
            profile_rollno.text = rollno.toString()
            profile_number.text = number.toString()
            profile_email.text = email.toString()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }
}