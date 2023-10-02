package com.example.submit_andro_funda

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username:String
    ):Call<DetailUserRespo>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username:String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username:String
    ):Call<ArrayList<User>>

}