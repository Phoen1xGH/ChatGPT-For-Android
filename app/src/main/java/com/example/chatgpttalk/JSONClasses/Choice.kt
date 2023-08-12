
package com.example.chatgpttalk.JSONClasses
import com.google.gson.annotations.SerializedName

class Choice (
    @SerializedName("index") val Index : Int,
    @SerializedName("message") val Message : Message,
    @SerializedName("finish_reasons") val FinishReasons : String
)