package com.ed.mygithub.reposlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ed.mygithub.BaseFragment
import com.ed.mygithub.databinding.ReposFragBinding
import kotlinx.android.synthetic.main.repos_frag.*

class ReposFragment : BaseFragment() {
    private lateinit var mParentActivity: ReposActivity
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mParentActivity = activity as ReposActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ReposFragBinding.inflate(inflater, container, false).apply {
            viewmodel = mParentActivity.obtainViewModel()
            viewModel = mParentActivity.obtainViewModel()
            viewmodel?.run {
                showLoader.observe(mParentActivity, Observer { showLoader(it) })
                snackbar.observe(mParentActivity, Observer { showSnackbar(it) })
            }
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onStop() {
        viewModel.unsubscribe()
        super.onStop()
    }

    private fun setUpRecyclerView() {
        recyclerView = repos_recycler_view
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = ReposAdapter(context!!)
        recyclerView.adapter = recyclerViewAdapter
    }

    companion object {
        fun newInstance() = ReposFragment()
    }
}