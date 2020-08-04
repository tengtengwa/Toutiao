package com.example.toutiao.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.Fragment
import com.example.toutiao.R

/**
 * Fragment的基类，将一些公共方法抽到其中
 */
open class BaseFragment : Fragment() {

    private var isLoadedData = false

    protected lateinit var activity: Activity

    private lateinit var rootView: View

    private var loadingView: View? = null

    private var loadErrorView: View? = null

    private var viewStub: View? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireActivity()
    }

    fun onCreateView(view: View): View {
        rootView = view
        loadingView = rootView.findViewById(R.id.loadingView)
        return view
    }

    override fun onResume() {
        super.onResume()
        if (!isLoadedData) {
            loadData()
            isLoadedData = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (rootView.parent != null) (rootView.parent as ViewGroup).removeView(rootView)
    }

    open fun loadData() {
    }

    protected fun startLoading() {
        loadData()
        hideLoadErrorView()
    }

    protected fun showLoadErrorView() {
        viewStub = rootView.findViewById(R.id.loadErrorView)
        if (viewStub != null) {
            loadErrorView = (viewStub as ViewStub).inflate()
            loadErrorView?.visibility = View.VISIBLE
            loadErrorView?.setOnClickListener {
                loadData()
            }
        }
    }

    protected fun hideLoadErrorView() {
        loadErrorView?.visibility = View.INVISIBLE
    }
}