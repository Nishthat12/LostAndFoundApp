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
    private lateinit var Image1Uri: Uri
    private lateinit var Image2Uri: Uri
    private lateinit var Image3Uri: Uri
    private lateinit var Image4Uri: Uri
    private lateinit var Image5Uri: Uri

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

        binding.image1.setOnClickListener {
            selectImage1()
        }
        binding.image2.setOnClickListener {
            selectImage2()
        }
        binding.image3.setOnClickListener {
            selectImage3()
        }
        binding.image4.setOnClickListener {
            selectImage4()
        }
        binding.image5.setOnClickListener {
            selectImage5()
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
        val storage1 = FirebaseStorage.getInstance().getReference("images/$userID/$fileName/1")
        val storage2 = FirebaseStorage.getInstance().getReference("images/$userID/$fileName/2")
        val storage3 = FirebaseStorage.getInstance().getReference("images/$userID/$fileName/3")
        val storage4 = FirebaseStorage.getInstance().getReference("images/$userID/$fileName/4")
        val storage5 = FirebaseStorage.getInstance().getReference("images/$userID/$fileName/5")

//        if (Image2Uri != null && this::Image2Uri.isInitialized){
//            storage2.putFile(Image2Uri)
//        }
//        if (Image3Uri != null && this::Image3Uri.isInitialized){
//            storage3.putFile(Image3Uri)
//        }
//        if (Image4Uri != null && this::Image4Uri.isInitialized){
//            storage4.putFile(Image4Uri)
//        }
//        if (Image5Uri != null && this::Image5Uri.isInitialized){
//            storage5.putFile(Image5Uri)
//        }
//        if (Image1Uri != null && this::Image1Uri.isInitialized){
//
//        }
        storage1.putFile(Image1Uri)
            .addOnSuccessListener {
                Toast.makeText(this@Post_LostActivity, "Image uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(this@Post_LostActivity, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
    }

    private fun selectImage1() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    private fun selectImage2() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 200)

    }
    private fun selectImage3() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 300)

    }
    private fun selectImage4() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 400)

    }
    private fun selectImage5() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 500)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Image1Uri = data?.data!!
            binding.image1.setImageURI(Image1Uri)
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Image2Uri = data?.data!!
            binding.image2.setImageURI(Image2Uri)
        }
        if (requestCode == 300 && resultCode == RESULT_OK) {
            Image3Uri = data?.data!!
            binding.image3.setImageURI(Image3Uri)
        }
        if (requestCode == 400 && resultCode == RESULT_OK) {
            Image4Uri = data?.data!!
            binding.image4.setImageURI(Image4Uri)
        }
        if (requestCode == 500 && resultCode == RESULT_OK) {
            Image5Uri = data?.data!!
            binding.image5.setImageURI(Image5Uri)
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