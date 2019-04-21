package com.ed.mygithub

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ed.Injection
import com.ed.mygithub.data.source.GitHubRepository
import com.ed.mygithub.login.LoginViewModel
import com.ed.mygithub.reposlist.ReposViewModel

class ViewModelFactory private constructor(
    private val application: Application,
    private val gitHubRepository: GitHubRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(gitHubRepository)
                isAssignableFrom(ReposViewModel::class.java) ->
                    ReposViewModel(gitHubRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    application,
                    Injection.provideGitHubRepository(application.applicationContext)
                )
                    .also { INSTANCE = it }
            }


        fun destroyInstance() {
            INSTANCE = null
        }
    }
}