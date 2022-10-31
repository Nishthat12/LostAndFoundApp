package com.example.lostandfoundappcs230

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import com.bumptech.glide.Glide
import com.example.lostandfoundappcs230.databinding.FragmentHomePageBinding
import com.example.lostandfoundappcs230.databinding.FragmentProfilePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfilePage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfilePageBinding.inflate(layoutInflater)
        val view : View = inflater.inflate(R.layout.fragment_profile_page,container,false)
        val profileImage : ImageView = view.findViewById(R.id.profile_img_profile)
        val profileName : TextView = view.findViewById(R.id.user_profile_name)
        val profileRoll : TextView = view.findViewById(R.id.user_rollno)
        val profileNumber : TextView = view.findViewById(R.id.user_number)
        val profileEmail : TextView = view.findViewById(R.id.user_email)

        val db = FirebaseDatabase.getInstance().getReference("Users")
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        db.child(userID).get().addOnSuccessListener {
            val profile_img = it.child("profileUrl").value
            val profile_name = it.child("name").value
            val profile_rollno = it.child("rollNumber").value
            val profile_number = it.child("contactNumber").value
            val profile_email = it.child("email").value
            if (profile_img != null){
                Glide.with(this)
                    .load(profile_img).into(profileImage)
            }
            profileName.text = profile_name.toString()
            profileRoll.text = profile_rollno.toString()
            profileNumber.text = profile_number.toString()
            profileEmail.text = profile_email.toString()
        }

        return binding.root
    }
}