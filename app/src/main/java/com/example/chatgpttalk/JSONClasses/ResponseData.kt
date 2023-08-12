package com.example.chatgpttalk.JSONClasses

import com.example.chatgpttalk.JSONClasses.Choice
import com.google.gson.annotations.SerializedName

class ResponseData (
    @SerializedName("id") val Id : String,
    @SerializedName("object") val Object : String,
    @SerializedName("created") val Created : ULong,
    @SerializedName("choices") val Choices : List<Choice>,
    @SerializedName("usage") val Usage : Usage
)