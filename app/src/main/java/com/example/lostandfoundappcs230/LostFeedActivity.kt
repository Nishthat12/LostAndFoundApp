package com.example.lostandfoundappcs230

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LostFeedActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var thingsList: ArrayList<Lost_things>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide() //hide the title bar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_feed)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        thingsList = arrayListOf()

        db = FirebaseFirestore.getInstance()
        db.collection("Lost Items").get().addOnSuccessListener {
            if (!it.isEmpty){
                for (data in it.documents){
                    db.collection("user").document()
                    val thing: Lost_things? =  data.toObject(Lost_things::class.java)
                    if (thing != null) {
                        thingsList.add(thing )
                    }
                }
                recyclerView.adapter = MyAdapter(thingsList)
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}