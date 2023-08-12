package com.example.chatgpttalk.JSONClasses

import com.google.gson.annotations.SerializedName

class RequestGPT (
    @SerializedName("model") val ModelId : String,
    @SerializedName("messages") val Messages : List<Message>
)