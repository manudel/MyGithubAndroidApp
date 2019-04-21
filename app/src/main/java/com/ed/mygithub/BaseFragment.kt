package com.ed.mygithub

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    lateinit var viewModel: BaseViewModel
    lateinit var viewDataBinding: ViewDataBinding
    lateinit var progressSpiner: ProgressBar

    fun showSnackbar(@StringRes message: Int?) {
        message?.let {
            Snackbar.make(view!!, it, Snackbar.LENGTH_SHORT).apply {
                show()
            }
        }
    }

    fun showLoader(show: Boolean?) {
        progressSpiner.visibility = if (show == true) View.VISIBLE else View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressSpiner = view.findViewById(R.id.progress_spinner)
    }
}