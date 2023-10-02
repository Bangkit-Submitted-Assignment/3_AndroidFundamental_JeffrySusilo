package com.example.submit_andro_funda

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submit_andro_funda.databinding.FragmentFollowBinding

class FollowingFrag: Fragment(R.layout.fragment_follow) {

    private var _binding:FragmentFollowBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewMod
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args=arguments
        username=args?.getString(DetailUserAct.EXTRA_USERNAME).toString()
        _binding=FragmentFollowBinding.bind(view)
        adapter= UserAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager= LinearLayoutManager(activity)
            rvUser.adapter=adapter
        }
        loading(true)
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewMod::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                loading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility= View.VISIBLE
        }else{
            binding.progressBar.visibility=View.GONE
        }
    }
}