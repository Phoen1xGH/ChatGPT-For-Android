package com.example.chatgpttalk.JSONClasses

import com.google.gson.annotations.SerializedName

class Message(
    @SerializedName("role") val Role : String,
    @SerializedName("content") val Content : String
)