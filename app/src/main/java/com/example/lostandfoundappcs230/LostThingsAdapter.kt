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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LostThingsAdapter(private val context: Context, private val thingsList: ArrayList<Things>) :
    RecyclerView.Adapter<LostThingsAdapter.MyViewHolder>() {

    private lateinit var apiService: APIService

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
        val foundBt: Button = itemView.findViewById(R.id.claim_button)
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

        val userID = thingsList[position].userID

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService::class.java)

        holder.Message.canScrollVertically(1)

        holder.foundBt.setOnClickListener {
            val oguserID = FirebaseAuth.getInstance().currentUser!!.uid
            var name:String? = null
            var number:String? = null
            FirebaseDatabase.getInstance().getReference("Users").child(oguserID).get().addOnSuccessListener {
                name = it.child("name").value.toString()
                number = it.child("contactNumber").value.toString()
            }
            FirebaseDatabase.getInstance().reference.child("Tokens").child(userID!!).child("token")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val usertoken: String = snapshot.getValue(String::class.java).toString()
                        sendNotification(
                            usertoken,
                            "Someone wants to claim an item you found!",
                            "You can contact- $name \n Phone Number: $number"
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
        updateToken()

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


    private fun updateToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val token: String? = it.result
                    val userID = FirebaseAuth.getInstance().currentUser!!.uid
                    FirebaseDatabase.getInstance().getReference("Tokens").child(userID).child("token").setValue(token)
                }
            }
    }

    private fun sendNotification(usertoken:String,title: String,message: String){
        val data=Data(title,message)
        val sender= NotificationSender(data,usertoken)
        apiService.sendNotifcation(sender)!!.enqueue(object : Callback<MyResponse?> {

            override fun onResponse(call: Call<MyResponse?>, response: Response<MyResponse?>) {
                if (response.code() == 200) {
                    if (response.body()!!.success != 1) {
                        Toast.makeText(context, "Failed ", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<MyResponse?>, t: Throwable) {
            }
        })
    }

    override fun getItemCount(): Int {
        return thingsList.size
    }
}