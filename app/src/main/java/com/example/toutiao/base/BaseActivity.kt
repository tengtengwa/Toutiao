package com.example.toutiao.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.utils.BarUtils
import com.example.toutiao.R
import com.example.toutiao.utils.ActivityCollector
import permissions.dispatcher.RuntimePermissions
import java.lang.ref.WeakReference

@RuntimePermissions
open class BaseActivity : AppCompatActivity() {

    protected lateinit var activity: Activity

    protected lateinit var activityWR: WeakReference<Activity>

    protected var isActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this
        activityWR = WeakReference(this)
        ActivityCollector.addToStack(activityWR)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        BarUtils.setColor(this, R.color.colorPrimaryDark)
        setUpViews()
    }

    protected open fun setUpViews() {
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeFromStack(activityWR)
    }
}
