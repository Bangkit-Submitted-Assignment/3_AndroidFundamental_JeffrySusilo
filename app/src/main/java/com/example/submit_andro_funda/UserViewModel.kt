package com.example.submit_andro_funda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String) {
        // Pastikan parameter query adalah String biasa (non-nullable)
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.items
                        Log.d("Response", "Data received: $data")
                        listUsers.postValue(data)
                    } else {
                        Log.e("Response", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}
