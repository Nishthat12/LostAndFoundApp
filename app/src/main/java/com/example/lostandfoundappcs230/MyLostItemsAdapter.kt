package com.example.lostandfoundappcs230

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyLostItemsAdapter(
    private val context: Context,
    private val myLostThingsList: ArrayList<Things>
) :
    RecyclerView.Adapter<MyLostItemsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.my_lost_name)
        val Number: TextView = itemView.findViewById(R.id.my_lost_number)
        val Message: TextView = itemView.findViewById(R.id.my_lost_message)
        val Where: TextView = itemView.findViewById(R.id.my_lost_where)
        val Image1: ImageView = itemView.findViewById(R.id.image1)
        val Image2: ImageView = itemView.findViewById(R.id.image2)
        val Image3: ImageView = itemView.findViewById(R.id.image3)
        val Image4: ImageView = itemView.findViewById(R.id.image4)
        val Image5: ImageView = itemView.findViewById(R.id.image5)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_lost_items, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lostThing = myLostThingsList[position]
        holder.Name.text = myLostThingsList[position].name
        holder.Number.text = myLostThingsList[position].phoneNumber
        holder.Message.text = myLostThingsList[position].message
        holder.Where.text = myLostThingsList[position].whereLost

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
        return myLostThingsList.size
    }
}