package com.example.toutiao.ui.activity

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.toutiao.R
import com.example.toutiao.base.BaseActivity
import com.example.toutiao.utils.NetworkUtil
import com.example.toutiao.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        if (!NetworkUtil.isNetworkConnected(this)) {
            "网络未连接，请检查网络设置".toast(this)
        }

        toast = "再按一次退出应用".toast(this)
        val navView: BottomNavigationView = nav_view
        val navController = findNavController(R.id.nav_host_fragment)
/*        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_news, R.id.navigation_video, R.id.navigation_weather))
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (toast.view.parent == null) {
            toast.show()
        } else {
            finish()
        }
    }
}
