package com.example.toutiao.activity.ui.news

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

import com.example.toutiao.R

class TopNewsFragment : Fragment() {

    companion object {
        fun newInstance() = TopNewsFragment()
    }

    private lateinit var viewModel: TopNewsViewModel

    private lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
