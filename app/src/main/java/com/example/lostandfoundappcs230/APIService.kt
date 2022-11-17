package com.example.lostandfoundappcs230


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAwxOsVIM:APA91bEiaydjCpSvPb9OEnyWajOa2RecIKyE0GUUt9I7uzfiUjce1adJTEGc179jG5FCMMfXNoyKpStTl8eZIpA9_IuRcHIblTOp8m_RubgrXL53jDyguSa4JES7KMBbkEB8fKTlr_3B" // Your server key refer to video for finding your server key
    )
    @POST("fcm/send")
    fun sendNotifcation(@Body body: NotificationSender?): Call<MyResponse?>?
}