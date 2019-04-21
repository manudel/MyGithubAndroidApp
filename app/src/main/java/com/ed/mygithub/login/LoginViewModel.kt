package com.ed.mygithub.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.ed.mygithub.BaseViewModel
import com.ed.mygithub.R
import com.ed.mygithub.data.source.GitHubRepository
import com.ed.mygithub.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel(private val gitHubRepository: GitHubRepository) : BaseViewModel() {
    val openReposEvent = SingleLiveEvent<Void>()
    val account: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()
    val unauthorized = ObservableBoolean(false)

    override fun start() {
        hideUnauthorizedWarning()

        val accountValue = account.get() ?: ""
        val passwordValue = password.get() ?: ""

        if (accountValue.isNotEmpty() && passwordValue.isNotEmpty()) {
            disposable.add(
                gitHubRepository.login(accountValue, passwordValue)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { showLoadingIndicator() }
                    .subscribe(
                        { onSuccess(it) },
                        { onError(it) }
                    )
            )
        } else {
            showEmptyInputWarning()
        }
    }

    private fun onSuccess(it: Boolean) {
        Timber.w("success: $it")
        hideUnauthorizedWarning()
        hideLoadingIndicator()
        openReposEvent.call()
    }

    private fun onError(t: Throwable) {
        Timber.w("error $t")
        hideLoadingIndicator()
        t.message?.let {
            if (isUnauthorizedAccess(it)) {
                resetLoginFields()
                showUnauthorizedWarning()
            }
        }
    }

    private fun isUnauthorizedAccess(s: String) = s.contains("401")

    private fun resetLoginFields() {
        account.set("")
        password.set("")
    }


    private fun showUnauthorizedWarning() {
        unauthorized.set(true)
    }

    private fun hideUnauthorizedWarning() {
        unauthorized.set(false)
    }

    private fun showEmptyInputWarning() {
        snackbar.value = R.string.empty_input
    }

    fun login() {
        Timber.w("Login clicked. Account: $account")
        start()
    }
}