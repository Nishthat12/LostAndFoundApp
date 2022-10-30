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
import androidx.viewpager.widget.ViewPager
import com.example.lostandfoundappcs230.databinding.ActivityPostLostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Post_LostActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var selectBtn: Button
    private lateinit var multipleImagesUri: ArrayList<Uri>

    private lateinit var binding: ActivityPostLostBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var Image1Uri: Uri
    private lateinit var Image2Uri: Uri
    private lateinit var Image3Uri: Uri
    private lateinit var Image4Uri: Uri
    private lateinit var Image5Uri: Uri

    private lateinit var username:String

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereLost: EditText
    private lateinit var submitBtn: Button
    private lateinit var postPhotoBtn: Button
    lateinit var image1url: String


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityPostLostBinding.inflate(layoutInflater) //initialize binding
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()  //initialize storage

        val userID = FirebaseAuth.getInstance().currentUser!!.uid  //get userid
        readData(userID)  //function to read user data for name and roll number

//        viewPager = findViewById(R.id.view_pager)
//        selectBtn = findViewById(R.id.post_lost_select)

//        selectBtn.setOnClickListener {
//            selectMultipleImages()
//        }

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

        //post photo button on click listener
        postPhotoBtn.setOnClickListener {
            uploadImage()
        }

        //uploads name etc data to firestore database
        submitBtn.setOnClickListener {

            val sName = etName.text.toString().trim()
            val sPhoneNumber = etPhoneNumber.text.toString().trim()
            val sMessage = etMessage.text.toString().trim()
            val sWhereLost = etWhereLost.text.toString().trim()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)
//
            val userMap = hashMapOf(
                "name" to sName,
                "phoneNumber" to sPhoneNumber,
                "message" to sMessage,
                "whereLost" to sWhereLost,
                "imageURL" to image1url
            )
            //collects data and adds to firestore
            db.collection("user").document(userID).collection("Lost Items").document(fileName).set(userMap)
            db.collection("Lost Items").document(fileName).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Posted", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etPhoneNumber.text.clear()
                    etMessage.text.clear()
                    etWhereLost.text.clear()
                    binding.image1.setImageResource(R.drawable.resource_new)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show()
                }
        }
    }

//    private fun selectMultipleImages() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        startActivityForResult(intent, 69)
//        if (multipleImagesUri != null) {
//            multipleImagesUri.clear()
//        }
//    }

    //function to upload image firebase storage
    private fun uploadImage() {
        //shows a progress dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("images/$username/$fileName/1")

        storage.putFile(Image1Uri)
            .addOnSuccessListener {
                Toast.makeText(this@Post_LostActivity, "Image uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage.downloadUrl.addOnSuccessListener {
                    image1url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this@Post_LostActivity, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
    }


    //function to select a image and display it
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

//        if (requestCode == 69 && resultCode == RESULT_OK) {
//            if (data != null) {
//                var count = data.clipData!!.itemCount
//                for (i in 0 until count) {
//                    multipleImagesUri.add(data.clipData!!.getItemAt(i).uri)
//                }
//            }else{
//                multipleImagesUri.add(data?.data!!)
//            }
//
//            Image1Uri = data?.data!!
//            binding.image1.setImageURI(Image1Uri)
//        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Image1Uri = data?.data!!
            binding.image1.setImageURI(Image1Uri)
        }
//        if (requestCode == 300 && resultCode == RESULT_OK) {
//            Image3Uri = data?.data!!
//            binding.image3.setImageURI(Image3Uri)
//        }
//        if (requestCode == 400 && resultCode == RESULT_OK) {
//            Image4Uri = data?.data!!
//            binding.image4.setImageURI(Image4Uri)
//        }
//        if (requestCode == 500 && resultCode == RESULT_OK) {
//            Image5Uri = data?.data!!
//            binding.image5.setImageURI(Image5Uri)
//        }
    }

    //function to read the name and number from realtime database
    private fun readData(userID: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userID).get().addOnSuccessListener {

            val name = it.child("name").value
            val number = it.child("contactNumber").value
            username = it.child("email").value.toString()
            etName.text = name.toString().toEditable()
            etPhoneNumber.text = number.toString().toEditable()

        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}