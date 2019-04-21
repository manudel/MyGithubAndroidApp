package com.ed

import android.content.Context
import com.ed.mygithub.api.GitHubService
import com.ed.mygithub.data.source.GitHubRepository
import com.ed.mygithub.data.source.remote.GitHubRemoteDataSource

/**
 * Enables injection of production implementations for
 * [GitHubDataSource] at compile time.
 */
object Injection {
    fun provideGitHubRepository(context: Context): GitHubRepository {
        val gitHubService = GitHubService.create()
        return GitHubRepository.getInstance(GitHubRemoteDataSource.getInstance(gitHubService))
    }
}