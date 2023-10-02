package com.example.submit_andro_funda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewMod(application: Application): AndroidViewModel(application){
    val user=MutableLiveData<DetailUserRespo>()
    private var userDao : FavUserDao?
    private var userDatabase : DatabaseUser?
    init {
        userDatabase= DatabaseUser.getDatabase(application)
        userDao=userDatabase?.favUserDao()
    }

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserRespo>{
                override fun onResponse(
                    call: Call<DetailUserRespo>,
                    response: Response<DetailUserRespo>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserRespo>, t: Throwable) {

                }

            })
    }
    fun getUserDetail():LiveData<DetailUserRespo>{
        return user
    }
    fun addToFav(username: String, id:Int,avatarurl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user=FavUser(
                username,
                id,
                avatarurl
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun cekUser(id: Int)=userDao?.cekUser(id)
    fun removeDrFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.HapusDrFav(id)
        }
    }
}