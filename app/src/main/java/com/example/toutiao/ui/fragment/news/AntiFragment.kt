package com.example.toutiao.ui.fragment.news

import android.annotation.SuppressLint
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
import com.example.toutiao.adapter.AntipNewsAdapter
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.AntipNewsModel
import com.example.toutiao.ui.activity.DetailsActivity
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.fragment_antip.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AntiFragment : Fragment() {

    private lateinit var viewModel: AntipViewModel

    private val mainScope = MainScope()

    private val newsList = ArrayList<AntipNewsModel.AntipNewsList>()

    private val antipNewsAdapter = AntipNewsAdapter(newsList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_antip, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = BaseApplication.context
        viewModel = ViewModelProviders.of(this).get(AntipViewModel::class.java)

        if (!NetworkUtil.isNetworkConnected(context)) {
            "当前网络未连接".toast(context)
        }

        if (!viewModel.isNewsSaved()) {     //首次进入刷新页面
            fetchTopNews()
            antipNewsAdapter.notifyDataSetChanged()
        } else {
            newsList.addAll(viewModel.getSavedNews())
        }

        srl_antip.setOnRefreshListener {
            fetchTopNews()
            antipNewsAdapter.notifyDataSetChanged()
            srl_antip.finishRefresh(1500)
        }
        srl_antip.setOnLoadMoreListener {
            loadMoreNews()
            antipNewsAdapter.notifyDataSetChanged()
            srl_antip.finishLoadMore(1500)
        }

        rv_antip_news.apply {
            adapter = antipNewsAdapter
            layoutManager = LinearLayoutManager(context)
            antipNewsAdapter.onListItemClicked = object : AntipNewsAdapter.OnListItemClicked {
                override fun onItemClick(view: View, pos: Int) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("url", newsList[pos].url)
                    startActivity(intent)
                }
            }
        }

        viewModel.antipNewsList.observe(viewLifecycleOwner, Observer {
            newsList.addAll(it)
            Log.d("ttw", "topNewsList size: ${newsList.size}")
            antipNewsAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchTopNews() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.refreshNews()

        withContext(Dispatchers.Main) {
            if (viewModel.antipNewsList.value != null) {
                viewModel.antipNewsList.value!!.addAll(list)
            } else {
                viewModel.antipNewsList.value = list
            }
            newsList.addAll(list.subList(2, list.size - 1))
            antipNewsAdapter.notifyDataSetChanged()
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
            if (viewModel.antipNewsList.value != null) {
                viewModel.antipNewsList.value!!.addAll(viewModel.antipNewsList.value!!.size - 1, list)
            } else {
                viewModel.antipNewsList.value = list
            }
            newsList.addAll(list.subList(2, list.size - 1))
            antipNewsAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }

}
