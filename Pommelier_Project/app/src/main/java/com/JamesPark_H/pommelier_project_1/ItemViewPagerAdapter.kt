package com.JamesPark_H.pommelier_project_1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ItemViewPagerAdapter(
    fragmentManager: FragmentManager,
    val tabCount : Int
): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0-> {
                return FragmentRecommendation()
            }
            1-> {
                return FragmentRank()
            }
            else-> {
                return FragmentCollection()
            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}