package com.example.lostandfoundappcs230

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val context: Context, private val thingsList: ArrayList<Things>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.feed_name)
        val Number: TextView = itemView.findViewById(R.id.feed_number)
        val Message: TextView = itemView.findViewById(R.id.feed_message)
        val Where: TextView = itemView.findViewById(R.id.feed_where)
        val Image1: ImageView = itemView.findViewById(R.id.image1)
        val Image2: ImageView = itemView.findViewById(R.id.image2)
        val Image3: ImageView = itemView.findViewById(R.id.image3)
        val Image4: ImageView = itemView.findViewById(R.id.image4)
        val Image5: ImageView = itemView.findViewById(R.id.image5)
        val lostBt: Button = itemView.findViewById(R.id.feed_found_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.lost_item_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lostThing = thingsList[position]
        holder.Name.text = thingsList[position].name
        holder.Number.text = thingsList[position].phoneNumber
        holder.Message.text = thingsList[position].message
        holder.Where.text = thingsList[position].whereLost

        holder.lostBt.setOnClickListener {
            Toast.makeText(context,"clickedd",Toast.LENGTH_SHORT).show() 
        }

        Glide.with(context)
            .load(lostThing.image1URL).into(holder.Image1)
        Glide.with(context)
            .load(lostThing.image2URL).into(holder.Image2)
        Glide.with(context)
            .load(lostThing.image3URL).into(holder.Image3)
        Glide.with(context)
            .load(lostThing.image4URL).into(holder.Image4)
        Glide.with(context)
            .load(lostThing.image5URL).into(holder.Image5)
    }

    override fun getItemCount(): Int {
        return thingsList.size
    }
}