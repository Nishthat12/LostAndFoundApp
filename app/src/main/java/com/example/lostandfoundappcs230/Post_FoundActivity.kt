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
import com.example.lostandfoundappcs230.databinding.ActivityPostFoundBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Post_FoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostFoundBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private var Image1Uri: Uri? = null
    private var Image2Uri: Uri? = null
    private var Image3Uri: Uri? = null
    private var Image4Uri: Uri? = null
    private var Image5Uri: Uri? = null

    private lateinit var username: String

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereLost: EditText
    private lateinit var submitBtn: Button
    private lateinit var postPhotoBtn: Button
    private var image1url: String? = null
    private var image2url: String? = null
    private var image3url: String? = null
    private var image4url: String? = null
    private var image5url: String? = null


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        binding = ActivityPostFoundBinding.inflate(layoutInflater) //initialize binding
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()  //initialize storage

        val userID = FirebaseAuth.getInstance().currentUser!!.uid  //get userid
        readData(userID)  //function to read user data for name and roll number

        etName = findViewById(R.id.post_found_name)
        etPhoneNumber = findViewById(R.id.post_found_number)
        etMessage = findViewById(R.id.post_found_message)
        etWhereLost = findViewById(R.id.post_found_where)
        submitBtn = findViewById(R.id.Btn_post_found_submit)
        postPhotoBtn = findViewById(R.id.post_found_photo)

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
            if (Image1Uri != null || Image2Uri != null || Image3Uri != null || Image4Uri != null || Image5Uri != null)
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

            val userMap = hashMapOf(
                "name" to sName,
                "phoneNumber" to sPhoneNumber,
                "message" to sMessage,
                "whereLost" to sWhereLost,
                "image1URL" to image1url,
                "image2URL" to image2url,
                "image3URL" to image3url,
                "image4URL" to image4url,
                "image5URL" to image5url,
                "userID" to userID
            )
            //collects data and adds to firestore
            db.collection("user").document(userID).collection("Found Items").document(fileName)
                .set(userMap)
            db.collection("Found Items").document(fileName).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Posted", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etPhoneNumber.text.clear()
                    etMessage.text.clear()
                    etWhereLost.text.clear()
                    binding.image1.setImageResource(R.drawable.resource_new)
                    binding.image2.setImageResource(R.drawable.resource_new)
                    binding.image3.setImageResource(R.drawable.resource_new)
                    binding.image4.setImageResource(R.drawable.resource_new)
                    binding.image5.setImageResource(R.drawable.resource_new)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show()
                }

            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    //function to upload image firebase storage
    private fun uploadImage() {
//        shows a progress dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storage1 = FirebaseStorage.getInstance().getReference("images/$username/$fileName/1")
        val storage2 = FirebaseStorage.getInstance().getReference("images/$username/$fileName/2")
        val storage3 = FirebaseStorage.getInstance().getReference("images/$username/$fileName/3")
        val storage4 = FirebaseStorage.getInstance().getReference("images/$username/$fileName/4")
        val storage5 = FirebaseStorage.getInstance().getReference("images/$username/$fileName/5")
        if (Image1Uri != null) {
            storage1.putFile(Image1Uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this@Post_FoundActivity, "1st Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage1.downloadUrl.addOnSuccessListener {
                        image1url = it.toString()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Post_FoundActivity, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
        }
        if (Image2Uri != null) {
            storage2.putFile(Image2Uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this@Post_FoundActivity, "2nd Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage2.downloadUrl.addOnSuccessListener {
                        image2url = it.toString()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Post_FoundActivity, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
        }
        if (Image3Uri != null) {
            storage3.putFile(Image3Uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this@Post_FoundActivity, "3rd Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage3.downloadUrl.addOnSuccessListener {
                        image3url = it.toString()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Post_FoundActivity, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
        }
        if (Image4Uri != null) {
            storage4.putFile(Image4Uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this@Post_FoundActivity, "4th Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage4.downloadUrl.addOnSuccessListener {
                        image4url = it.toString()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Post_FoundActivity, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
        }
        if (Image5Uri != null) {
            storage5.putFile(Image5Uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this@Post_FoundActivity, "5th Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage5.downloadUrl.addOnSuccessListener {
                        image5url = it.toString()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this@Post_FoundActivity, "Failed", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }
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