package com.example.lostandfoundappcs230

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyFoundItemsAdapter(
    private val context: Context,
    private val myFoundThingsList: ArrayList<Things>,
    private val myFoundThingsIdList: ArrayList<String>
) :
    RecyclerView.Adapter<MyFoundItemsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.my_found_name)
        val Number: TextView = itemView.findViewById(R.id.my_found_number)
        val Message: TextView = itemView.findViewById(R.id.my_found_message)
        val Where: TextView = itemView.findViewById(R.id.my_found_where)
        val Image1: ImageView = itemView.findViewById(R.id.image1)
        val Image2: ImageView = itemView.findViewById(R.id.image2)
        val Image3: ImageView = itemView.findViewById(R.id.image3)
        val Image4: ImageView = itemView.findViewById(R.id.image4)
        val Image5: ImageView = itemView.findViewById(R.id.image5)

        val FoundEditBtn: Button = itemView.findViewById(R.id.my_found_edit)
        val FoundDeleteBtn: Button = itemView.findViewById(R.id.my_found_delete)

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

        holder.FoundEditBtn.setOnClickListener {
            val intent = Intent(context, LostPostEdit::class.java)
            intent.putExtra("message", myFoundThingsList[position].message)
            intent.putExtra("whereLost", myFoundThingsList[position].whereLost)
            intent.putExtra("image1", lostThing.image1URL)
            intent.putExtra("image2", lostThing.image2URL)
            intent.putExtra("image3", lostThing.image3URL)
            intent.putExtra("image4", lostThing.image4URL)
            intent.putExtra("image5", lostThing.image5URL)
            intent.putExtra("filename", myFoundThingsIdList[position])
            context.startActivity(intent)
        }

        holder.FoundDeleteBtn.setOnClickListener {
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            val db = FirebaseFirestore.getInstance()
            db.collection("user").document(userID).collection("Found Items").document(
                myFoundThingsIdList[position]
            ).delete().addOnSuccessListener {
                context.startActivity(Intent(context, MyFoundPosts::class.java))
            }
            db.collection("Found Items").document(myFoundThingsIdList[position]).delete()
        }
    }

    override fun getItemCount(): Int {
        return myFoundThingsList.size
    }
}