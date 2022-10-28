package com.example.lostandfoundappcs230

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val thingsList: ArrayList<Lost_things>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.feed_name)
        val Number: TextView = itemView.findViewById(R.id.feed_number)
        val Message: TextView = itemView.findViewById(R.id.feed_message)
        val Where: TextView = itemView.findViewById(R.id.feed_where)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Name.text = thingsList[position].name
        holder.Number.text = thingsList[position].phoneNumber
        holder.Message.text = thingsList[position].message
        holder.Where.text = thingsList[position].whereLost
    }

    override fun getItemCount(): Int {
        return thingsList.size
    }
}