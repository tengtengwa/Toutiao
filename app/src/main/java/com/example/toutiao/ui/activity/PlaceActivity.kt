package com.example.toutiao.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toutiao.R
import com.example.toutiao.adapter.PlaceAdapter
import com.example.toutiao.base.BaseActivity
import com.example.toutiao.logic.model.Place
import com.example.toutiao.ui.fragment.weather.WeatherFragment
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.activity_change_place.*

class PlaceActivity : BaseActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_place)

        adapter = PlaceAdapter(viewModel.placeList)
        //选择完地点返回天气页面
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.onListItemClicked = object : PlaceAdapter.OnListItemClicked {
            override fun onItemClick(view: View, pos: Int) {
                Log.d("ttw", "clicked")
                val place = viewModel.placeList[pos]
                viewModel.savePlace(place)
                val intent = Intent(this@PlaceActivity, MainActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        et_search_place.addTextChangedListener {
            Log.d("ttw", it.toString())
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                iv_bg.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this, Observer {
            val places = it.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                iv_bg.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                "未搜索到相关地点，请重新输入".toast(this)
                it.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}
