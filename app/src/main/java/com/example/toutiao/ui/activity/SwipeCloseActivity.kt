package com.example.toutiao.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import com.example.toutiao.base.BaseActivity
import com.jude.swipbackhelper.SwipeBackHelper

open class SwipeCloseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwipeBackHelper.onCreate(this)
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)//on-off
                .setSwipeEdge(100)//set the touch area。200 mean only the left 200px of screen can touch to begin swipe.
                .setSwipeEdgePercent(0.2f)//0.2 mean left 20% of screen can touch to begin swipe.
                .setSwipeSensitivity(0.5f)//sensitiveness of the gesture。0:slow  1:sensitive
                .setScrimColor(Color.TRANSPARENT)//滑动时背景活动前的颜色
                .setClosePercent(0.8f)//close activity when swipe over this
                .setSwipeRelateEnable(false)//if should move together with the following Activity
                .setSwipeRelateOffset(500)//the Offset of following Activity when setSwipeRelateEnable(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        SwipeBackHelper.finish(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }
}