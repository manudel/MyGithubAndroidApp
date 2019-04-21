package com.ed.mygithub.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ed.mygithub.BaseFragment
import com.ed.mygithub.databinding.LoginFragBinding

class LoginFragment : BaseFragment() {
    private lateinit var mParentActivity: LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        mParentActivity = activity as LoginActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = LoginFragBinding.inflate(inflater, container, false).apply {
            viewmodel = mParentActivity.obtainViewModel()
            viewModel = mParentActivity.obtainViewModel()
            viewmodel?.run {
                showLoader.observe(mParentActivity, Observer { showLoader(it) })
                snackbar.observe(mParentActivity, Observer { showSnackbar(it) })
            }
        }
        return viewDataBinding.root
    }

    override fun onStop() {
        viewModel.unsubscribe()
        super.onStop()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}