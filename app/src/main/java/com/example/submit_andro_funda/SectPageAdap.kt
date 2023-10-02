package com.example.submit_andro_funda

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectPageAdap(private val mCon: Context, fragmentActivity: FragmentActivity, data: Bundle) :

    FragmentStateAdapter(fragmentActivity) {

    private var fragmentBundle:Bundle
    init {
        fragmentBundle=data
    }
    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2)

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFrag().apply { arguments = fragmentBundle }
            1 -> FollowingFrag().apply { arguments = fragmentBundle }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    fun getPageTitle(position: Int): CharSequence? {
        return mCon.resources.getString(TAB_TITLES[position])
    }
}
