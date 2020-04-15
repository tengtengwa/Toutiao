package com.example.toutiao.ui.fragment.video

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
import com.example.toutiao.adapter.VideoAdapter
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.VideoModel
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoFragment : Fragment() {

    private lateinit var viewModel: VideoViewModel

    private val mainScope = MainScope()

    private val videoList = ArrayList<VideoModel.VideoList>()

    private val videoAdapter = VideoAdapter(videoList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = BaseApplication.context
        viewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)

        if (!NetworkUtil.isNetworkConnected(context)) {
            "当前网络未连接".toast(context)
        }

        if (!viewModel.isNewsSaved()) {     //首次进入刷新页面
            fetchVideoList()
            videoAdapter.notifyDataSetChanged()
        } else {
            videoList.addAll(viewModel.getSavedNews())
        }

        srl_video.setOnRefreshListener {
            fetchVideoList()
            videoAdapter.notifyDataSetChanged()
            srl_video.finishRefresh(1500)
        }
        srl_video.setOnLoadMoreListener {
            loadMoreVideoList()
            videoAdapter.notifyDataSetChanged()
            srl_video.finishLoadMore(1500)
        }

        rv_video.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
/*            topNewsAdapter.onListItemClicked = object : VideoAdapter.OnListItemClicked {
                override fun onItemClick(view: View, pos: Int) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("url", newsList[pos].url)
                    startActivity(intent)
                }
            }*/
        }

        viewModel.videoList.observe(viewLifecycleOwner, Observer {
            videoList.addAll(it)
            Log.d("ttw", "topNewsList size: ${videoList.size}")
            videoAdapter.notifyDataSetChanged()
        })
    }

    private fun fetchVideoList() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.refreshNews()
        withContext(Dispatchers.Main) {
            if (viewModel.videoList.value != null) {
                viewModel.videoList.value!!.addAll(0, list)
            } else {
                viewModel.videoList.value = list
            }
            videoList.addAll(0, list)
            videoAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }

    override fun onDestroy() {
        viewModel.saveWhenExit()
        super.onDestroy()
    }

    private fun loadMoreVideoList() = mainScope.launch(Dispatchers.IO) {
        val list = viewModel.loadMore()
        withContext(Dispatchers.Main) {
            if (viewModel.videoList.value != null) {
                viewModel.videoList.value!!.addAll(list)
            } else {
                viewModel.videoList.value = list
            }
            videoList.addAll(list)
            videoAdapter.notifyDataSetChanged()
            viewModel.saveNews()
        }
    }
}
