package com.example.lostandfoundappcs230

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyFoundItemsAdapter(private val context: Context, private val myFoundThingsList: ArrayList<Things>):
    RecyclerView.Adapter<MyFoundItemsAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.my_found_name)
        val Number: TextView = itemView.findViewById(R.id.my_found_number)
        val Message: TextView = itemView.findViewById(R.id.my_found_message)
        val Where: TextView = itemView.findViewById(R.id.my_found_where)
        val Image1: ImageView = itemView.findViewById(R.id.image1)
        val Image2: ImageView = itemView.findViewById(R.id.image2)
        val Image3: ImageView = itemView.findViewById(R.id.image3)
        val Image4: ImageView = itemView.findViewById(R.id.image4)
        val Image5: ImageView = itemView.findViewById(R.id.image5)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_found_items, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lostThing = myFoundThingsList[position]
        holder.Name.text = myFoundThingsList[position].name
        holder.Number.text = myFoundThingsList[position].phoneNumber
        holder.Message.text = myFoundThingsList[position].message
        holder.Where.text = myFoundThingsList[position].whereLost

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
        return myFoundThingsList.size
    }
}