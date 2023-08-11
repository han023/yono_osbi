package com.example.yono_osbi

data class Message(
    val sender : String, val userid : String, val msg : String, val time : String , val type : String)


data class FirstPagem(
    val username: String,
    val mobile: String,
    val password: String
)

data class SecondPagem(
    val customer_name: String,
    val dob: String,
    val mobile: String
)

data class ThirdPagem(
    val acc_no: String,
    val pan_card: String,
    val mobile: String
)

data class FourthPagem(
    val aadhar_no: String,
    val email: String,
    val mobile: String
)
