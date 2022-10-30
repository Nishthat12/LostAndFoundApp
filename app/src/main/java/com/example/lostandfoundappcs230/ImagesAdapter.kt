//package com.example.lostandfoundappcs230
//
//
//import android.content.Context.LAYOUT_INFLATER_SERVICE
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.core.content.ContextCompat.getSystemServiceName
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentStatePagerAdapter
//import androidx.viewpager.widget.PagerAdapter
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.google.firebase.database.core.Context
//
//
//class ImagesAdapter(fragmentManager: FragmentManager, private val multipleImagesUri: ArrayList<Uri>):
//FragmentStatePagerAdapter(fragmentManager){
//    lateinit var context: Context
//    val layoutInflater
//    override fun getCount(): Int {
//        return multipleImagesUri.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view = layoutInflater.inflate(R.layout.images_single,container,false)
//
//        val imageView =
//    }
//    override fun getItem(position: Int): Fragment {
//
//    }
//
//
//}