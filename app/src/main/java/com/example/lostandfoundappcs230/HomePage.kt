package com.example.lostandfoundappcs230

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lostandfoundappcs230.databinding.FragmentHomePageBinding


class HomePage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind = FragmentHomePageBinding.inflate(layoutInflater)

        bind.PostLostCard.setOnClickListener {
            val intent = Intent(this@HomePage.requireContext(), Post_LostActivity::class.java)
            startActivity(intent)
        }
        bind.PostFoundCard.setOnClickListener {
            val intent = Intent(this@HomePage.requireContext(), Post_FoundActivity::class.java)
            startActivity(intent)
        }
        bind.FeedLostCard.setOnClickListener {
            val intent = Intent(this@HomePage.requireContext(), LostFeedActivity::class.java)
            startActivity(intent)
        }
        bind.FeedFoundCard.setOnClickListener {
            val intent = Intent(this@HomePage.requireContext(), FoundFeedActivity::class.java)
            startActivity(intent)
        }
        return bind.root
    }

}