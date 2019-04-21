package com.ed.mygithub.reposlist

import androidx.databinding.ObservableArrayList
import com.ed.mygithub.BaseViewModel
import com.ed.mygithub.data.Repository
import com.ed.mygithub.data.User
import com.ed.mygithub.data.source.GitHubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ReposViewModel(private val gitHubRepository: GitHubRepository) : BaseViewModel() {

    val items = ObservableArrayList<ShortRepo>()

    override fun start() {
        disposable.add(
            gitHubRepository.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onSuccess(it)
                    }, {
                        Timber.w("error: $it")
                    }
                )
        )
    }

    private fun onSuccess(results: List<Repository>) {
        Timber.w("success")
        val shortRepos =
            results.take(REPOS_TO_DISPLAY).map {
                var avatarUrl: String? = null
                val own = it.owner
                if (own is User) {
                    avatarUrl = own.avatar_url
                }
                ShortRepo(it.name, it.description, avatarUrl)
            }
                .sortedBy { it.name }
        items.addAll(shortRepos)
    }

    companion object {
        const val REPOS_TO_DISPLAY = 10
    }
}

data class ShortRepo(val name: String, val description: String, val avatarUrl: String?)