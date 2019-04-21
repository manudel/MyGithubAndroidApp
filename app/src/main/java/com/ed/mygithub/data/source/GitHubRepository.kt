package com.ed.mygithub.data.source

import com.ed.mygithub.data.Repository
import io.reactivex.Single

class GitHubRepository(private val gitHubRemoteDataSource: GitHubDataSource) : GitHubDataSource {

    override fun login(user: String, password: String): Single<Boolean> {
        return gitHubRemoteDataSource.login(user, password)
    }

    override fun getRepositories(): Single<List<Repository>> {
        return gitHubRemoteDataSource.getRepositories()
    }

    companion object {

        private var INSTANCE: GitHubRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * *
         * @return the [GitHubRepository] instance
         */
        @JvmStatic
        fun getInstance(remoteDataSource: GitHubDataSource) =
            INSTANCE ?: synchronized(GitHubRepository::class.java) {
                INSTANCE
                    ?: GitHubRepository(remoteDataSource)
                        .also { INSTANCE = it }
            }
    }
}