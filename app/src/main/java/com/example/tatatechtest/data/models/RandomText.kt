package com.example.tatatechtest.data.models

data class RandomText(
    val value: String,
    val length: Int,
    val created: String
)

data class RandomTextResponse(
    val randomText: RandomText
)