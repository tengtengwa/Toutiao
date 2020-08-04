package com.example.toutiao.ui.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.toutiao.R
import com.example.toutiao.adapter.SectionsPagerAdapter
import com.example.toutiao.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        view_pager.currentItem = 0
        tab_layout.setupWithViewPager(view_pager)
    }

}
