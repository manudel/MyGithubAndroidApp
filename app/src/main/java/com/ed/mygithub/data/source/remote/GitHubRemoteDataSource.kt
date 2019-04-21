package com.ed.mygithub.data.source.remote

import com.ed.mygithub.api.BasicAuthInterceptor
import com.ed.mygithub.api.GitHubService
import com.ed.mygithub.data.Organization
import com.ed.mygithub.data.RepoType
import com.ed.mygithub.data.Repository
import com.ed.mygithub.data.User
import com.ed.mygithub.data.source.GitHubDataSource
import com.ed.mygithub.util.RuntimeTypeAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class GitHubRemoteDataSource private constructor(private val gitHubService: GitHubService) : GitHubDataSource {

    override fun login(user: String, password: String): Single<Boolean> {
        Timber.w("Adding user and pass to the Interceptor - $user : $password")
        BasicAuthInterceptor.user = user
        BasicAuthInterceptor.password = password

        // 401 error is handled in the Login viewmodel
        return gitHubService.login()
            .flatMap {
                Single.just(true)
            }
    }

    val adapter = RuntimeTypeAdapterFactory
        .of(RepoType::class.java)
        .registerSubtype(User::class.java)
        .registerSubtype(Organization::class.java)

    override fun getRepositories(): Single<List<Repository>> {
        return gitHubService.getRepositories()
            .flatMap { extractObjectList<Repository>(it) }
    }

    private inline fun <reified T> extractObjectList(body: ResponseBody): Single<List<T>> {
        body.use {
            val response = it.string()

            if (response.isEmpty()) {
                Timber.w("Returning empty list")
                return Single.just(emptyList())
            }

            val gson = Gson()
            val array = gson.fromJson(response, JsonArray::class.java)

            val myGson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create()

            val entries: List<T> = array.map { myGson.fromJson(it, T::class.java) }
            return Observable.fromArray(entries).singleOrError()
        }
    }

    companion object {
        private var INSTANCE: GitHubRemoteDataSource? = null

        @JvmStatic
        fun getInstance(gitHubService: GitHubService): GitHubRemoteDataSource {
            if (GitHubRemoteDataSource.INSTANCE == null) {
                synchronized(GitHubRemoteDataSource::javaClass) {
                    GitHubRemoteDataSource.INSTANCE = GitHubRemoteDataSource(gitHubService)
                }
            }
            return GitHubRemoteDataSource.INSTANCE!!
        }
    }
}