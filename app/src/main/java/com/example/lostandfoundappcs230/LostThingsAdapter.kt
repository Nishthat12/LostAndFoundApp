package com.example.lostandfoundappcs230

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LostThingsAdapter(private val context: Context, private val thingsList: ArrayList<Things>) :
    RecyclerView.Adapter<LostThingsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.feed_found_name)
        val Number: TextView = itemView.findViewById(R.id.feed_found_number)
        val Message: TextView = itemView.findViewById(R.id.feed_found_message)
        val Where: TextView = itemView.findViewById(R.id.feed_found_where)
        val Image1: ImageView = itemView.findViewById(R.id.image1_found)
        val Image2: ImageView = itemView.findViewById(R.id.image2_found)
        val Image3: ImageView = itemView.findViewById(R.id.image3_found)
        val Image4: ImageView = itemView.findViewById(R.id.image4_found)
        val Image5: ImageView = itemView.findViewById(R.id.image5_found)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.found_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val foundThing = thingsList[position]
        holder.Name.text = thingsList[position].name
        holder.Number.text = thingsList[position].phoneNumber
        holder.Message.text = thingsList[position].message
        holder.Where.text = thingsList[position].whereLost

        Glide.with(context)
            .load(foundThing.image1URL).into(holder.Image1)
        Glide.with(context)
            .load(foundThing.image2URL).into(holder.Image2)
        Glide.with(context)
            .load(foundThing.image3URL).into(holder.Image3)
        Glide.with(context)
            .load(foundThing.image4URL).into(holder.Image4)
        Glide.with(context)
            .load(foundThing.image5URL).into(holder.Image5)
    }

    override fun getItemCount(): Int {
        return thingsList.size
    }
}