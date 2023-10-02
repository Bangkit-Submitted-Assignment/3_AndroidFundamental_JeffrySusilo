package com.example.submit_andro_funda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavViewMod(application: Application): AndroidViewModel(application) {
    private var userDao : FavUserDao?
    private var userDatabase : DatabaseUser?
    init {
        userDatabase= DatabaseUser.getDatabase(application)
        userDao=userDatabase?.favUserDao()
    }
    fun getFavUser(): LiveData<List<FavUser>>?{
        return userDao?.getFavUser()
    }
}