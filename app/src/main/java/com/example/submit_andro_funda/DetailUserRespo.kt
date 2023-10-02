package com.example.submit_andro_funda

data class DetailUserRespo(
    val login:String,
    val id:Int,
    val avatar_url:String,
    val followers:Int,
    val following: Int,
    val name : String,
    /*val following_url:String,
    val followers_url: String*/
)