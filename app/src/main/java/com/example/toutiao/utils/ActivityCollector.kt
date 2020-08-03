package com.example.toutiao.utils

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.ArrayDeque

object ActivityCollector {

    private val stack = ArrayDeque<WeakReference<Activity>>()

    fun addToStack(activityWR: WeakReference<Activity>) = stack.add(activityWR)

    fun removeFromStack(activityWR: WeakReference<Activity>) = stack.remove(activityWR)

    fun clearStack() = stack.clear()

}