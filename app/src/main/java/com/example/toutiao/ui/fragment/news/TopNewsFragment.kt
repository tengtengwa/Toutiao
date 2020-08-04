package com.example.toutiao.ui.fragment.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toutiao.R
import com.example.toutiao.adapter.TopNewsAdapter
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.base.BaseFragment
import com.example.toutiao.logic.model.TopNewsModel
import com.example.toutiao.ui.activity.DetailsActivity
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.fragment_topnews.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopNewsFragment : BaseFragment() {

    private lateinit var viewModel: TopNewsViewModel

    private val mainScope = MainScope()

    private val newsList = ArrayList<TopNewsModel.TopNewsList>()

    private val topNewsAdapter = TopNewsAdapter(newsList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topnews, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = BaseApplication.context
        viewModel = ViewModelProviders.of(this).get(TopNewsViewModel::class.java)

        if (!NetworkUtil.isNetworkConnected(context)) {
            "当前网络未连接".toast(context)
        }

        if (!viewModel.isNewsSaved()) {     //首次进入刷新页面
            fetchTopNews()
            topNewsAdapter.notifyDataSetChanged()
        } else {
            newsList.addAll(viewModel.getSavedNews())
        }

        srl_top_news.setOnRefreshListener {
            fetchTopNews()
            topNewsAdapter.notifyDataSetChanged()
            srl_top_news.finishRefresh(1500)
        }
        srl_top_news.setOnLoadMoreListener {
            loadMoreNews()
            topNewsAdapter.notifyDataSetChanged()
            srl_top_news.finishLoadMore(1500)
        }

        rv_top_news.apply {
            adapter = topNewsAdapter
            layoutManager = LinearLayoutManager(context)
            topNewsAdapter.onListItemClicked = object : TopNewsAdapter.OnListItemClicked {
                override fun onItemClick(view: View, pos: Int) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("url", newsList[pos].url)
                    startActivity(intent)
                }
            }
        }

        viewModel.topNewsList.observe(viewLifecycleOwner, Observer {
            newsList.addAll(it)
            Log.d("ttw", "topNewsList size: ${newsList.size}")
            topNewsAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchTopNews() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.refreshNews()
        withContext(Dispatchers.Main) {
            if (viewModel.topNewsList.value != null) {
                viewModel.topNewsList.value!!.addAll(list.subList(2, list.size - 1))
            } else {
                viewModel.topNewsList.value = list
            }
            newsList.addAll(0, list.subList(2, list.size - 1))
            topNewsAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }

    override fun onDestroy() {
        viewModel.saveWhenExit()
        super.onDestroy()
    }

    private fun loadMoreNews() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.loadMore()
        withContext(Dispatchers.Main) {
            if (viewModel.topNewsList.value != null) {
                viewModel.topNewsList.value!!.addAll(list.subList(2, list.size - 1))
            } else {
                viewModel.topNewsList.value = list
            }
            newsList.addAll(list.subList(2, list.size - 1))
            topNewsAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }
}
