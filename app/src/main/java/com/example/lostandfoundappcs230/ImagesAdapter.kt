package com.example.lostandfoundappcs230

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ImagesAdapter(private val LostImagesList: List<LostImages>) :
    RecyclerView.Adapter<ImagesAdapter.LostImagesViewHolder>() {


    class LostImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val LostImagesView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images, parent, false)
        return LostImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LostImagesViewHolder, position: Int) {
        val lostImages = LostImagesList[position]
        holder.LostImagesView.setImageURI(lostImages.LostImageUri)
    }

    override fun getItemCount(): Int {
        return LostImagesList.size
    }
}