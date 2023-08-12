package com.example.chatgpttalk.JSONClasses

import com.google.gson.annotations.SerializedName

class Usage (
    @SerializedName("prompt_tokens") val PromptTokens : String,
    @SerializedName("completion_tokens") val CompletionTokens : String,
    @SerializedName("total_tokens") val TotalTokens : String
)
