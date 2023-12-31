package com.example.submit_andro_funda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavUser::class],
    version = 1
)
abstract class DatabaseUser: RoomDatabase() {
    companion object{
        var INSTANCE : DatabaseUser?=null
        fun getDatabase(context: Context): DatabaseUser?{
            if (INSTANCE==null){
                kotlin.synchronized(DatabaseUser::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,DatabaseUser::class.java,"database_user").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favUserDao() : FavUserDao
}