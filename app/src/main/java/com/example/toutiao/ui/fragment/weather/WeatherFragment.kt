package com.example.toutiao.ui.fragment.weather

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.toutiao.R
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.Place
import com.example.toutiao.logic.model.WeatherModel
import com.example.toutiao.logic.model.getSky
import com.example.toutiao.ui.activity.PlaceActivity
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val context = BaseApplication.context
        if (viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            viewModel.apply {
                placeName = place.name
                lng = place.location.lng
                lat = place.location.lat
            }
        } else {
            startActivityForResult(Intent(context, PlaceActivity::class.java), 1)
        }
        refreshWeather()
        btn_change_place.setOnClickListener {
            startActivityForResult(Intent(context, PlaceActivity::class.java), 1)
        }

        srl_weather.setOnRefreshListener {
            srl_weather.isRefreshing = true
            refreshWeather()
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherData(weather)
                srl_weather.isRefreshing = false
            } else {
                "数据更新失败，请检查网络设置".toast(context)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ttw", data?.getStringExtra("place_name") ?: "")
        data?.apply {
            viewModel.placeName = getStringExtra("place_name")!!
            viewModel.lng = getStringExtra("location_lng")!!
            viewModel.lat = getStringExtra("location_lat")!!
            Log.d("ttw", "placeName:${viewModel.placeName}")
        }
        refreshWeather()
    }

    private fun showWeatherData(weather: WeatherModel) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        val temp = "${realtime.temperature.toInt()} ℃"
        val aqi = "空气指数 ${realtime.airCon.aqi.chn.toInt()}"
        currentTemp.text = temp
        currentSky.text = getSky(realtime.skycon).info
        currentAQI.text = aqi
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(context).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }

        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

    private fun refreshWeather() {
        viewModel.refreshWeather(viewModel.lng, viewModel.lat)
    }
}
