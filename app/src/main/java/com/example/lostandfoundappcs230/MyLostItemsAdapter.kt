package com.example.lostandfoundappcs230

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent

class MyLostItemsAdapter(
    private val context: Context,
    private val myLostThingsList: ArrayList<Things>,
    private val myLostThingsIdList: ArrayList<String>
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

        val LostEditBtn: Button = itemView.findViewById(R.id.my_lost_edit)
        val LostDeleteBtn: Button = itemView.findViewById(R.id.my_lost_delete)

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

        holder.LostEditBtn.setOnClickListener {
            val intent = Intent(context, LostPostEdit::class.java)
            intent.putExtra("message", myLostThingsList[position].message)
            intent.putExtra("whereLost", myLostThingsList[position].whereLost)
            intent.putExtra("image1", lostThing.image1URL)
            intent.putExtra("image2", lostThing.image2URL)
            intent.putExtra("image3", lostThing.image3URL)
            intent.putExtra("image4", lostThing.image4URL)
            intent.putExtra("image5", lostThing.image5URL)
            intent.putExtra("filename", myLostThingsIdList[position])
            println(myLostThingsIdList[position])
            context.startActivity(intent)
        }

        holder.LostDeleteBtn.setOnClickListener {
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            val db = FirebaseFirestore.getInstance()
            db.collection("user").document(userID).collection("Lost Items").document(
                myLostThingsIdList[position]
            ).delete().addOnSuccessListener {
               context.startActivity(Intent(context, MyLostPostsActivity::class.java))
            }
            db.collection("Lost Items").document(myLostThingsIdList[position]).delete()
        }
    }

    override fun getItemCount(): Int {
        return myLostThingsList.size
    }
}