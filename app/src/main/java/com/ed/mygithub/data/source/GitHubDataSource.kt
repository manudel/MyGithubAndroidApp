package com.ed.mygithub.data.source

import com.ed.mygithub.data.Repository
import io.reactivex.Single

interface GitHubDataSource {
    fun login(user: String, password: String): Single<Boolean>

    fun getRepositories(): Single<List<Repository>>
}