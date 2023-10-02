package com.example.submit_andro_funda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submit_andro_funda.databinding.SearchUserBinding


class SearchUser : AppCompatActivity() {
    private lateinit var binding: SearchUserBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: UserViewModel
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super .onCreate(savedInstanceState)
        binding = SearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences
        sharedPrefs = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

        // Periksa preferensi tema yang disimpan
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)

        // Terapkan tema sesuai dengan preferensi
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        adapter=UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClick(data: User) {
                Intent(this@SearchUser, DetailUserAct::class.java).also {
                    it.putExtra(DetailUserAct.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserAct.EXTRA_ID,data.id)
                    it.putExtra(DetailUserAct.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        binding.apply {
            daftaruser.layoutManager=LinearLayoutManager(this@SearchUser)
            daftaruser .setHasFixedSize(true)
            daftaruser.adapter=adapter

            cari.setOnClickListener{
                searchuser()
            }

        }

        viewModel.getSearchUsers().observe(this,{
            if (it!=null){
                adapter.setList(it)
                loading(false)
            }
        })
    }

    private fun searchuser(){
        binding.apply {
            val query=searchView.query.toString()
            if (query.isEmpty()) return
            loading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun loading(state: Boolean){
        if (state){
            binding.progresbar.visibility= View.VISIBLE
        }else{
            binding.progresbar.visibility=View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opsi_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorit_buton->{
                Intent(this, FavAct::class.java).also {
                    startActivity(it)
                }
            }
            R.id.change_theme -> {
                // Toggle tema (jika tema saat ini adalah gelap, maka ubah ke terang, dan sebaliknya)
                val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
                val editor = sharedPrefs.edit()
                editor.putBoolean("dark_mode", !isDarkMode)
                editor.apply()

                // Terapkan perubahan tema
                if (!isDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
