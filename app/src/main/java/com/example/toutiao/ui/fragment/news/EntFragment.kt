package com.example.toutiao.ui.fragment.news

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.toutiao.R
import com.example.toutiao.adapter.EntNewsAdapter
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.base.BaseFragment
import com.example.toutiao.logic.model.EntNewsModel
import com.example.toutiao.ui.activity.DetailsActivity
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.fragment_entertainment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntFragment : BaseFragment() {

    private lateinit var viewModel: EntViewModel

    private val mainScope = MainScope()

    private val newsList = ArrayList<EntNewsModel.EntNewsList>()

    private val sportNewsAdapter = EntNewsAdapter(newsList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EntViewModel::class.java)

        val context = BaseApplication.context

        if (!NetworkUtil.isNetworkConnected(context)) {
            "当前网络未连接".toast(context)
        }

        if (!viewModel.isNewsSaved()) {
            fetchTopNews()
            sportNewsAdapter.notifyDataSetChanged()
        } else {
            newsList.addAll(viewModel.getSavedNews())
        }

        srl_ent.setOnRefreshListener {
            fetchTopNews()
            sportNewsAdapter.notifyDataSetChanged()
            srl_ent.finishRefresh(1500)
        }
        srl_ent.setOnLoadMoreListener {
            loadMoreNews()
            sportNewsAdapter.notifyDataSetChanged()
            srl_ent.finishLoadMore(1500)
        }

        rv_ent_news.apply {
            adapter = sportNewsAdapter
            layoutManager = LinearLayoutManager(context)
            sportNewsAdapter.onListItemClicked = object : EntNewsAdapter.OnListItemClicked {
                override fun onItemClick(view: View, pos: Int) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("url", newsList[pos].url)
                    startActivity(intent)
                }
            }
        }

        viewModel.entNewsList.observe(viewLifecycleOwner, Observer {
            newsList.addAll(it)
            Log.d("ttw", "topNewsList size: ${newsList.size}")
            sportNewsAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchTopNews() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.refreshNews()
        withContext(Dispatchers.Main) {
            if (viewModel.entNewsList.value != null) {
                viewModel.entNewsList.value!!.addAll(0, list.subList(2, list.size - 1))
            } else {
                viewModel.entNewsList.value = list
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
            if (viewModel.entNewsList.value != null) {
                viewModel.entNewsList.value!!.addAll(list)
            } else {
                viewModel.entNewsList.value = list
            }
            newsList.addAll(list.subList(2, list.size - 1))
            sportNewsAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }

}
