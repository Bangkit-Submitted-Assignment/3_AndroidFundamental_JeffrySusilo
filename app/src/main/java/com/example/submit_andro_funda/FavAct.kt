package com.example.submit_andro_funda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submit_andro_funda.databinding.ActivityFavBinding

class FavAct : AppCompatActivity() {
    private lateinit var binding : ActivityFavBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewMod: FavViewMod
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter=UserAdapter()
        adapter.notifyDataSetChanged()
        viewMod=ViewModelProvider(this).get(FavViewMod::class.java)
        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClick(data: User) {
                Intent(this@FavAct, DetailUserAct::class.java).also {
                    it.putExtra(DetailUserAct.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserAct.EXTRA_ID,data.id)
                    it.putExtra(DetailUserAct.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager=LinearLayoutManager(this@FavAct)
            rvFav.adapter=adapter
        }
        viewMod.getFavUser()?.observe(this,{
            if (it!=null){
                val list=mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavUser>): ArrayList<User> {
        val listUsers=ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}