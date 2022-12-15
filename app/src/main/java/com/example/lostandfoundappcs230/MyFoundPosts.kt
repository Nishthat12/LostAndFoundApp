package com.example.lostandfoundappcs230

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyFoundPosts : AppCompatActivity() {
    private lateinit var lostPostsBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var thingsList: ArrayList<Things>
    private lateinit var thingsIdList: ArrayList<String>
    private var db = Firebase.firestore
    private lateinit var homeBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_found_posts)

        recyclerView = findViewById(R.id.recycler_view_my_found)
        recyclerView.layoutManager = LinearLayoutManager(this@MyFoundPosts)

        thingsList = arrayListOf()
        thingsIdList = arrayListOf()

        fetchData()

        lostPostsBtn = findViewById(R.id.my_lost_posts)
        homeBtn = findViewById(R.id.my_found_posts_home_button)

        homeBtn.setOnClickListener {
            val intent = Intent(this@MyFoundPosts, ProfilePageActivity::class.java)
            startActivity(intent)
        }
        lostPostsBtn.setOnClickListener {
            val intent = Intent(this@MyFoundPosts, MyLostPostsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fetchData() {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("user").document(userID).collection("Found Items").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach {
                        val docID = it.id
                        thingsIdList.add(docID)
                    }
                    for (data in it.documents) {
                        val things: Things? = data.toObject(Things::class.java)
                        if (things != null) {
                            thingsList.add(things)
                        }
                        recyclerView.adapter = MyFoundItemsAdapter(this, thingsList,thingsIdList)
                    }
                }
            }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}