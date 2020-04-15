package com.example.toutiao.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.toutiao.ui.fragment.news.AntiFragment
import com.example.toutiao.ui.fragment.news.EntFragment
import com.example.toutiao.ui.fragment.news.SportFragment
import com.example.toutiao.ui.fragment.news.TopNewsFragment

private val TAB_TITLES = arrayOf(
        "要闻",
        "抗疫",
        "娱乐",
        "体育"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        1 -> AntiFragment()
        2 -> EntFragment()
        3 -> SportFragment()
        else -> TopNewsFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 4
    }
}