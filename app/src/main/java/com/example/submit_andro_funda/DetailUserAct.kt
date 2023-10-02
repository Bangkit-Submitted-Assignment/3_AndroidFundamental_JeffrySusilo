package com.example.submit_andro_funda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submit_andro_funda.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserAct : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME ="extra_username"
        const val EXTRA_ID="extra_id"
        const val EXTRA_URL="extra_url"
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewMod
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username=intent.getStringExtra(EXTRA_USERNAME)?:""
        val id=intent.getIntExtra(EXTRA_ID,0)
        val avatarurl=intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)
        viewModel=ViewModelProvider(this).get(DetailUserViewMod::class.java)
        loading(true)
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this,{
            loading(false)
            if(it!=null){
                binding.apply {
                    namadetail.text=it.name
                    usernamedetail.text=it.login
                    tvFollowing.text="${it.following} Following"
                    tvFollower.text="${it.followers} Followers"
                    Glide.with(this@DetailUserAct)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(fotodetail)
                }
            }
        })
        var _isCek=false
        CoroutineScope(Dispatchers.IO).launch {
            val count=viewModel.cekUser(id)
            withContext(Dispatchers.Main){
                if(count!=null){
                    if(count>0){
                        binding.toggleFav.isChecked = true
                        _isCek=true
                    }else{
                        binding.toggleFav.isChecked = false
                        _isCek=false
                    }
                }
            }
        }
        binding.toggleFav.setOnClickListener {
            _isCek = !_isCek
            if (_isCek){
                viewModel.addToFav(username, id, avatarurl?:"")
            } else{
                viewModel.removeDrFav(id)
            }
            binding.toggleFav.isChecked=_isCek
        }
        val sectPageAdapter = SectPageAdap(this@DetailUserAct,this,bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectPageAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = sectPageAdapter.getPageTitle(position)
        }.attach()


    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}