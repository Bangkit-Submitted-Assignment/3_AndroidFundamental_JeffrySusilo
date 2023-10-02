package com.example.submit_andro_funda

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert
    fun addToFavorite(favoriteUser: FavUser)
    @Query("SELECT * FROM user_favorit")
    fun getFavUser(): LiveData<List<FavUser>>
    @Query("SELECT COUNT(*) FROM user_favorit WHERE id = :id")
    fun cekUser(id:Int): Int
    @Query("DELETE FROM user_favorit WHERE id= :id")
    fun HapusDrFav(id: Int): Int
}