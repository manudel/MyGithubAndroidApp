package com.ed.mygithub

import androidx.lifecycle.ViewModel
import com.ed.mygithub.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val disposable = CompositeDisposable()
    val showLoader = SingleLiveEvent<Boolean>()
    val snackbar = SingleLiveEvent<Int>()

    abstract fun start()

    fun unsubscribe() {
        disposable.clear()
    }

    fun showLoadingIndicator() {
        showLoader.value = true
    }

    fun hideLoadingIndicator() {
        showLoader.value = false
    }
}