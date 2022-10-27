package com.example.lostandfoundappcs230

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfoundappcs230.databinding.ActivityPostLostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Post_LostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostLostBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var ImageUri: Uri

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereLost: EditText
    private lateinit var submitBtn: Button
    private lateinit var postPhotoBtn: Button
    private lateinit var image1url: String


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityPostLostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        readData(userID)


        etName = findViewById(R.id.post_lost_name)
        etPhoneNumber = findViewById(R.id.post_lost_number)
        etMessage = findViewById(R.id.post_lost_message)
        etWhereLost = findViewById(R.id.post_lost_where)
        submitBtn = findViewById(R.id.Btn_post_lost_submit)
        postPhotoBtn = findViewById(R.id.post_lost_photo)

        binding.postLostSelect.setOnClickListener {
            selectImage()
        }

        postPhotoBtn.setOnClickListener {
            uploadImage()
        }

        //uploads name etc data to firestore database
        submitBtn.setOnClickListener {
            val storef = storage.reference
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)
            val finalRef = "images/$userID/$fileName.jpeg"

            val sName = etName.text.toString().trim()
            val sPhoneNumber = etPhoneNumber.text.toString().trim()
            val sMessage = etMessage.text.toString().trim()
            val sWhereLost = etWhereLost.text.toString().trim()
            storef.child(finalRef).downloadUrl.addOnSuccessListener {
                image1url = it.toString()
            }
            val userMap = hashMapOf(
                "name" to sName,
                "phoneNumber" to sPhoneNumber,
                "message" to sMessage,
                "whereLost" to sWhereLost,
                "imageURL" to image1url
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

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val finalRef = "images/$userID/$fileName"
        val storage = FirebaseStorage.getInstance().getReference(finalRef)
        print(ImageUri)
        storage.putFile(ImageUri)
            .addOnSuccessListener {
                Toast.makeText(this@Post_LostActivity, "Image uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(this@Post_LostActivity, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.image2.setImageURI(ImageUri)
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

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}