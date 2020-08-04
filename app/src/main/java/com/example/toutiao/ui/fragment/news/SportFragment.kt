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
import com.example.toutiao.adapter.SportNewsAdapter
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.base.BaseFragment
import com.example.toutiao.logic.model.SportNewsModel
import com.example.toutiao.ui.activity.DetailsActivity
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.fragment_sport.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SportFragment : BaseFragment() {

    private lateinit var viewModel: SportViewModel

    private val mainScope = MainScope()

    private val newsList = ArrayList<SportNewsModel.SportNewsList>()

    private val sportNewsAdapter = SportNewsAdapter(newsList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sport, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SportViewModel::class.java)
        val context = BaseApplication.context

        if (!NetworkUtil.isNetworkConnected(context)) {
            "当前网络未连接".toast(context)
        }

        if (!viewModel.isNewsSaved()) {     //首次进入刷新页面
            fetchTopNews()
            sportNewsAdapter.notifyDataSetChanged()
        } else {
            newsList.addAll(viewModel.getSavedNews())
        }

        if (!viewModel.isNewsSaved()) {
            fetchTopNews()
            sportNewsAdapter.notifyDataSetChanged()
        }

        srl_sport.setOnRefreshListener {
            fetchTopNews()
            sportNewsAdapter.notifyDataSetChanged()
            srl_sport.finishRefresh(1500)
        }
        srl_sport.setOnLoadMoreListener {
            loadMoreNews()
            sportNewsAdapter.notifyDataSetChanged()
            srl_sport.finishLoadMore(1500)
        }

        rv_sport_news.apply {
            adapter = sportNewsAdapter
            layoutManager = LinearLayoutManager(context)
            sportNewsAdapter.onListItemClicked = object : SportNewsAdapter.OnListItemClicked {
                override fun onItemClick(view: View, pos: Int) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("url", newsList[pos].url)
                    startActivity(intent)
                }
            }
        }

        viewModel.sportNewsList.observe(viewLifecycleOwner, Observer {
            newsList.addAll(it)
            Log.d("ttw", "topNewsList size: ${newsList.size}")
            sportNewsAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchTopNews() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.refreshNews()
        withContext(Dispatchers.Main) {
            if (viewModel.sportNewsList.value != null) {
                viewModel.sportNewsList.value!!.addAll(0, list.subList(2, list.size - 1))
            } else {
                viewModel.sportNewsList.value = list
            }
            newsList.addAll(0, list.subList(2, list.size - 1))
            sportNewsAdapter.notifyDataSetChanged()
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
            if (viewModel.sportNewsList.value != null) {
                viewModel.sportNewsList.value!!.addAll(list.subList(2, list.size - 1))
            } else {
                viewModel.sportNewsList.value = list
            }
            newsList.addAll(list.subList(2, list.size - 1))
            sportNewsAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }
}
